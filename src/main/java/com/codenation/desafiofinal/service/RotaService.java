package com.codenation.desafiofinal.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.codenation.desafiofinal.constante.ConstantesStatus;
import com.codenation.desafiofinal.exception.ResourceNotFoundException;
import com.codenation.desafiofinal.exception.RotaException;
import com.codenation.desafiofinal.model.EntregaPedido;
import com.codenation.desafiofinal.model.Estabelecimento;
import com.codenation.desafiofinal.model.JornadaRota;
import com.codenation.desafiofinal.model.Localizacao;
import com.codenation.desafiofinal.model.Motoboy;
import com.codenation.desafiofinal.model.Pedido;
import com.codenation.desafiofinal.model.Rota;
import com.codenation.desafiofinal.repository.EntregaPedidoRepository;
import com.codenation.desafiofinal.repository.EstabelecimentoRepository;
import com.codenation.desafiofinal.repository.MotoboyRepository;
import com.codenation.desafiofinal.repository.PedidoRepository;
import com.codenation.desafiofinal.util.MapFoodUtil;

@Service
public class RotaService {

	@Autowired
	private EstabelecimentoRepository estabelecimentoRepository;

	@Autowired
	private MotoboyRepository motoboyRepository;

	@Autowired
	private EntregaPedidoRepository entregaRepository;

	@Autowired
	private PedidoRepository pedidoRepository;

	@Autowired
	private GoogleMapService googleMapService;

	public Map<String, Localizacao> buscarInformacoes(EntregaPedido entrega, boolean isRotaMotoboyEstabelecimento) throws ResourceNotFoundException{

		//		if(entrega.getStatusEntrega().equals(ConstantesStatus.AGUARDANDO_RESPOSTA) && isRotaMotoboyEstabelecimento) {

		Map<String, Localizacao> mapEntidadeLocalizacao = buscarLocalizacaoMotoboyEEstabelecimento(entrega);
		return mapEntidadeLocalizacao;

		//		} else if(entrega.getStatusEntrega().equals(ConstantesStatus.EM_ANDAMENTO) && !isRotaMotoboyEstabelecimento) {

		//			Map<String, Localizacao> mapEntidadeLocalizacao = buscarLocalizacaoMotoboyEEstabelecimento(entrega);
		//			return mapEntidadeLocalizacao;
		//		}


		//		return null;
	}

	public Map<String, Localizacao> buscarLocalizacaoMotoboyEEstabelecimento(EntregaPedido entrega) throws ResourceNotFoundException {
		Map<String, Localizacao> mapEntidadeLocalizacao = new HashMap<String, Localizacao>();

		Estabelecimento estabelecimento = estabelecimentoRepository.findById(entrega.getEstabelecimento().getId()).orElseThrow(() -> new ResourceNotFoundException("Estabelecimento não encontrado ::" + entrega.getEstabelecimento().getId()));
		Motoboy motoboy = motoboyRepository.findById(entrega.getMotoboy().getId()).orElseThrow(() -> new ResourceNotFoundException("Motoboy não encontrado ::" + entrega.getMotoboy().getId()));

		mapEntidadeLocalizacao.put("motoboy", motoboy.getLocalizacao());
		mapEntidadeLocalizacao.put("estabelecimento", estabelecimento.getLocalizacao());

		return mapEntidadeLocalizacao;
	}

	public Rota definirRotaMotoboyEstabelecimento(Long idEntrega) throws ResourceNotFoundException, RotaException {
		EntregaPedido entrega = entregaRepository.findById(idEntrega).orElseThrow(() -> new ResourceNotFoundException("Entrega não encontrado ::" + idEntrega));

		Map<String, Localizacao> mapEntidadeLocalizacao = buscarInformacoes(entrega, true);

		if(!CollectionUtils.isEmpty(mapEntidadeLocalizacao) && mapEntidadeLocalizacao.size() == 2) {
			entrega.setStatusEntrega(ConstantesStatus.EM_ANDAMENTO);
			entrega.setDataIniciandoEntrega(MapFoodUtil.getDataAtual());
			entrega.getListaPedidos().forEach(pedido -> {
				try {

					Pedido pedidoBanco = pedidoRepository.findById(pedido.getId()).orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado ::" + idEntrega));
					pedidoBanco.setStatus(ConstantesStatus.EM_ANDAMENTO);
					pedidoRepository.save(pedidoBanco);

				} catch (ResourceNotFoundException e) {
					e.printStackTrace();
				}
			});

			Rota rota = googleMapService.buscarUnicaRota(mapEntidadeLocalizacao.get("motoboy"), mapEntidadeLocalizacao.get("estabelecimento"));

			if(entrega.getDistanciaPercorrida() == null) {
				entrega.setDistanciaPercorrida(0d);
			}

			Long tempoChegadaAoEstabelecimento = 0l;
			for (JornadaRota jornada : rota.getListaJornada()) {
				entrega.setDistanciaPercorrida(entrega.getDistanciaPercorrida() + jornada.getDistancia());
				tempoChegadaAoEstabelecimento = tempoChegadaAoEstabelecimento + jornada.getDuracao();
			}

			if(MapFoodUtil.converterSegundosEmMinutos(tempoChegadaAoEstabelecimento) < 10d) {
				entrega.setDistanciaPercorrida(10d);
			}

			entregaRepository.save(entrega);

			return rota;
		}else {
			throw new RotaException("Infelizmente não foi possível traçar uma rota para o estabelecimento, se o problema persistir contate o suporte!");
		}
	}

	public List<Rota> definirRotaParaEntregarPedidosAosClientes(Long idEntrega) throws ResourceNotFoundException {
		EntregaPedido entrega = entregaRepository.findById(idEntrega).orElseThrow(() -> new ResourceNotFoundException("Entrega não encontrado ::" + idEntrega));

		Map<String, Localizacao> mapEntidadeLocalizacao = buscarInformacoes(entrega, false);

		if(!CollectionUtils.isEmpty(mapEntidadeLocalizacao) && mapEntidadeLocalizacao.size() == 2) {
			entrega.setStatusEntrega(ConstantesStatus.PEDIDO_SAIU_PARA_ENTREGA);

			if(entrega.getListaPedidos().size() > 1) {
				Collections.sort(entrega.getListaPedidos());

				List<Pedido> listaPedidos = entrega.getListaPedidos();
				List<Localizacao> listaLocalizacao = new ArrayList<Localizacao>();

				Integer ultimoIndice = listaPedidos.size() - 1;
				Pedido ultimoPedido = listaPedidos.get(ultimoIndice);
				listaPedidos.remove(ultimoPedido);
				Localizacao destino = ultimoPedido.getCliente().getLocalizacao();

				for (Pedido pedido : listaPedidos) {
					Localizacao localizacao = pedido.getCliente().getLocalizacao();
					listaLocalizacao.add(localizacao);
				}

				List<Rota> listaRotas = googleMapService.buscarRotas(mapEntidadeLocalizacao.get("estabelecimento"), destino, listaLocalizacao);

				listaRotas.forEach(rota -> {
					rota.getListaJornada().forEach(jornada -> {
						entrega.setDistanciaPercorrida(entrega.getDistanciaPercorrida() + jornada.getDistancia());
					});
				});

				entregaRepository.save(entrega);

				return listaRotas;
			}else {

				Pedido pedido = entrega.getListaPedidos().get(0);
				Rota rota = googleMapService.buscarUnicaRota(mapEntidadeLocalizacao.get("estabelecimento"), pedido.getCliente().getLocalizacao());

				rota.getListaJornada().forEach(jornada -> {
					entrega.setDistanciaPercorrida(entrega.getDistanciaPercorrida() + jornada.getDistancia());
				});

				entregaRepository.save(entrega);

				return new ArrayList<Rota>(Arrays.asList(rota));
			}
		}

		return null;

	}

}
