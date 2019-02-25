package com.codenation.desafiofinal.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.codenation.desafiofinal.constante.ConstantesStatus;
import com.codenation.desafiofinal.exception.ResourceNotFoundException;
import com.codenation.desafiofinal.exception.RotaException;
import com.codenation.desafiofinal.model.EntregaPedido;
import com.codenation.desafiofinal.model.Estabelecimento;
import com.codenation.desafiofinal.model.Localizacao;
import com.codenation.desafiofinal.model.Motoboy;
import com.codenation.desafiofinal.model.Pedido;
import com.codenation.desafiofinal.model.Rota;
import com.codenation.desafiofinal.repository.EntregaPedidoRepository;
import com.codenation.desafiofinal.repository.EstabelecimentoRepository;
import com.codenation.desafiofinal.repository.MotoboyRepository;
import com.codenation.desafiofinal.repository.PedidoRepository;

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

	public Map<String, Localizacao> buscarInformacoes(EntregaPedido entrega) throws ResourceNotFoundException{

		if(entrega.getStatusEntrega().equals(ConstantesStatus.AGUARDANDO_RESPOSTA)) {
			Map<String, Localizacao> mapEntidadeLocalizacao = new HashMap<String, Localizacao>();

			Estabelecimento estabelecimento = estabelecimentoRepository.findById(entrega.getEstabelecimento().getId()).orElseThrow(() -> new ResourceNotFoundException("Estabelecimento não encontrado ::" + entrega.getEstabelecimento().getId()));
			Motoboy motoboy = motoboyRepository.findById(entrega.getMotoboy().getId()).orElseThrow(() -> new ResourceNotFoundException("Motoboy não encontrado ::" + entrega.getMotoboy().getId()));

			mapEntidadeLocalizacao.put("motoboy", motoboy.getLocalizacao());
			mapEntidadeLocalizacao.put("estabelecimento", estabelecimento.getLocalizacao());

			return mapEntidadeLocalizacao;
		}

		return null;
	}

	public Rota definirRotaMotoboyEstabelecimento(Long idEntrega) throws ResourceNotFoundException, RotaException {
		EntregaPedido entrega = entregaRepository.findById(idEntrega).orElseThrow(() -> new ResourceNotFoundException("Entrega não encontrado ::" + idEntrega));

		Map<String, Localizacao> mapEntidadeLocalizacao = buscarInformacoes(entrega);

		if(!CollectionUtils.isEmpty(mapEntidadeLocalizacao) && mapEntidadeLocalizacao.size() == 2) {
			entrega.setStatusEntrega(ConstantesStatus.EM_ANDAMENTO);
			entrega.getListaPedidos().forEach(pedido -> {
				try {

					Pedido pedidoBanco = pedidoRepository.findById(pedido.getId()).orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado ::" + idEntrega));
					pedidoBanco.setStatus(ConstantesStatus.EM_ANDAMENTO);
					pedidoRepository.save(pedidoBanco);

				} catch (ResourceNotFoundException e) {
					e.printStackTrace();
				}
			});

			entregaRepository.save(entrega);
			return googleMapService.buscarUnicaRota(mapEntidadeLocalizacao.get("motoboy"), mapEntidadeLocalizacao.get("estabelecimento"));
		}else {
			throw new RotaException("Infelizmente não foi possível traçar uma rota para o estabelecimento, se o problema persistir contate o suporte!");
		}
	}

	public void definirRotaParaEntregarPedidosAosClientes(Long idEntrega) {

	}

}
