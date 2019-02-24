package com.codenation.desafiofinal.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.codenation.desafiofinal.enums.StatusEnum;
import com.codenation.desafiofinal.exception.ResourceNotFoundException;
import com.codenation.desafiofinal.model.Cliente;
import com.codenation.desafiofinal.model.EntregaPedido;
import com.codenation.desafiofinal.model.Estabelecimento;
import com.codenation.desafiofinal.model.Localizacao;
import com.codenation.desafiofinal.model.Motoboy;
import com.codenation.desafiofinal.model.Pedido;
import com.codenation.desafiofinal.model.Produto;
import com.codenation.desafiofinal.repository.ClienteRepository;
import com.codenation.desafiofinal.repository.EntregaPedidoRepository;
import com.codenation.desafiofinal.repository.EstabelecimentoRepository;
import com.codenation.desafiofinal.repository.ItemPedidoRepository;
import com.codenation.desafiofinal.repository.MotoboyRepository;
import com.codenation.desafiofinal.repository.PedidoRepository;
import com.codenation.desafiofinal.repository.ProdutoRepository;
import com.codenation.desafiofinal.util.MapFoodUtil;

/**
 * Service do pedido, onde cria o pedido e tem toda a regra de negocio para a rota
 * e definir a regra para uma entrega ter até 5 pedidos para ser entregues.
 * @author Ricardo Lima
 *
 */
@Service
public class PedidoService {

	@Autowired
	private PedidoRepository repository;

	@Autowired
	private ItemPedidoRepository itemPedidoRepository;

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private EntregaPedidoRepository entregaRepository;

	@Autowired
	private MotoboyRepository motoboyRepository;

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private EstabelecimentoRepository estabelecimentoRepository;

	public Motoboy buscarMotoboyDisponivelMaisProximo(Localizacao localizacaoEstabelecimento) { //TODO CONCLUIR IMPLEMENTAÇÃO DO CODIGO DO RUBENS

		List<Motoboy> listaMotoboys = motoboyRepository.findAll(); //TODO IMPLEMENTAR FILTRO PRA ELIMINAR MOTOBOYS QUE TÃO COM ENTREGA EM ANDAMENTO
		List<Localizacao> listaLocalizacao = new ArrayList<Localizacao>();

		listaMotoboys.forEach(motoboy -> {
			Localizacao localizacao = new Localizacao();
			localizacao.setLatitude(motoboy.getLocalizacao().getLatitude());
			localizacao.setLongitude(motoboy.getLocalizacao().getLongitude());
			listaLocalizacao.add(localizacao);
		});

		Double lat, latMenor = null;
		Double lon, lonMenor = null;
		Double dist;
		Double menor = Double.MAX_VALUE;

		for (Localizacao localizacao : listaLocalizacao) {

			lat = Double.parseDouble(localizacao.getLatitude());
			lon = Double.parseDouble(localizacao.getLongitude());
			dist = MapFoodUtil.calcularDistanciaEntreDoisPontos(Double.parseDouble(localizacaoEstabelecimento.getLatitude()),Double.parseDouble(localizacaoEstabelecimento.getLongitude()), lat, lon);

			if(menor > dist) {
				latMenor = lat;
				lonMenor = lon;
				menor = dist;
			}
		}

		//		System.out.print(" DistÃ¢ncia "+ String.format("%.2f", menor) + " Kilometers\n");
		return motoboyRepository.findByLocalizacaoLatitudeAndLocalizacaoLongitude(latMenor.toString(), lonMenor.toString());
	}
	@Transactional(readOnly = false)
	public Pedido cadastrarPedido(Pedido pedido) throws ResourceNotFoundException {

		Pedido pedidoCompleto = completarInformacoesDoPedido(pedido);
		pedidoCompleto = determinarSeOPedidoEntraEmUmaEntregaExistente(pedidoCompleto);

		Motoboy motoboySelecionado = buscarMotoboyDisponivelMaisProximo(pedidoCompleto.getEstabelecimento().getLocalizacao());

		if(!pedidoCompleto.isPedidoIncluidoEmUmaEntrega()) {
			EntregaPedido entrega = new EntregaPedido(pedidoCompleto.getEstabelecimento(), MapFoodUtil.statusEnumToString(StatusEnum.EM_ANDAMENTO) , motoboySelecionado);
			entrega = entregaRepository.save(entrega);
			pedidoCompleto.setEntrega(entrega);
			pedidoCompleto.setStatus(MapFoodUtil.statusEnumToString(StatusEnum.EM_ANDAMENTO)); //TODO MUDAR DE STATUS ENUM PRA CONSTANTES (JA DEIXA EM STRING)
		}

		return repository.save(pedidoCompleto);
	}

	private Double calcularDistanciaEntreOsPedidos(Pedido novoPedido, Pedido pedidoJaRealizado) {

		Double lat1 = Double.parseDouble(pedidoJaRealizado.getCliente().getLocalizacao().getLatitude());
		Double lon1 = Double.parseDouble(pedidoJaRealizado.getCliente().getLocalizacao().getLongitude());
		Double lat2 = Double.parseDouble(novoPedido.getCliente().getLocalizacao().getLatitude());
		Double lon2 = Double.parseDouble(novoPedido.getCliente().getLocalizacao().getLongitude());
		Double distanciaCalculada = MapFoodUtil.calcularDistanciaEntreDoisPontos(lat1, lon1, lat2, lon2);

		return distanciaCalculada;
	}

	public Pedido completarInformacoesDoPedido(Pedido pedido) throws ResourceNotFoundException {

		pedido.setStatus(MapFoodUtil.statusEnumToString(StatusEnum.AGUARDANDO_RESPOSTA));
		pedido.setDataRealizacaoPedido(MapFoodUtil.getDataAtual());
		pedido.setPedidoIncluidoEmUmaEntrega(false);

		Cliente cliente = clienteRepository.findById(pedido.getCliente().getId())
				.orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado ::" + pedido.getCliente().getId()));

		pedido.setCliente(cliente);

		Estabelecimento estabelecimento = estabelecimentoRepository.findById(pedido.getEstabelecimento().getId())
				.orElseThrow(() -> new ResourceNotFoundException("Estabelecimento não encontrado ::" + pedido.getEstabelecimento().getId()));

		pedido.setEstabelecimento(estabelecimento);


		return completarInformacoesDoProdutoECalcularValorDoPedido(pedido);
	}

	public Pedido completarInformacoesDoProdutoECalcularValorDoPedido(Pedido pedido) {

		pedido.setValorTotal(0d);
		pedido.getListaItemPedido().forEach(item -> {
			try {

				Produto produto = produtoRepository.findById(item.getProduto().getId())
						.orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado ::" + item.getProduto().getId()));

				item.setProduto(produto);

				if(!item.getProduto().getEstabelecimento().getId().equals(pedido.getEstabelecimento().getId())) {
					throw new ResourceNotFoundException("O produto informado não pertence ao Estabelecimento escolhido!");
				}

				itemPedidoRepository.save(item);
				Double valorTotalItem = item.getProduto().getPreco() * item.getQuantidade();
				pedido.setValorTotal(pedido.getValorTotal() + valorTotalItem);

			} catch (ResourceNotFoundException e) {
				e.printStackTrace();
			}
		});

		return pedido;
	}
	public Pedido determinarSeOPedidoEntraEmUmaEntregaExistente(Pedido pedidoCompleto) {

		List<EntregaPedido> listaEntregasMesmoEstabelecimento = entregaRepository.findByEstabelecimentoIdAndStatusEntrega(pedidoCompleto.getEstabelecimento().getId(), MapFoodUtil.statusEnumToString(StatusEnum.EM_ANDAMENTO));

		for (EntregaPedido entrega : listaEntregasMesmoEstabelecimento) {
			if(!CollectionUtils.isEmpty(entrega.getListaPedidos()) && entrega.getListaPedidos().size() < 5) {
				for (Pedido pd : entrega.getListaPedidos()) {

					if(pedidoCompleto.getEntrega() != null ) {
						break;
					}

					Double distanciaCalculada = calcularDistanciaEntreOsPedidos(pedidoCompleto, pd);

					if(MapFoodUtil.getMinutosFromTimes(pd.getDataRealizacaoPedido(), pedidoCompleto.getDataRealizacaoPedido()) <= 2 && distanciaCalculada <= 5d) {
						pedidoCompleto.setPedidoIncluidoEmUmaEntrega(true);
						pedidoCompleto.setEntrega(entrega);
						pedidoCompleto.setStatus(MapFoodUtil.statusEnumToString(StatusEnum.EM_ANDAMENTO));
					}
				}
			}
		}

		if(pedidoCompleto.getEntrega() != null) {
			Integer indexEntrega = listaEntregasMesmoEstabelecimento.indexOf(pedidoCompleto.getEntrega());
			EntregaPedido entrega = listaEntregasMesmoEstabelecimento.get(indexEntrega);
			entrega.getListaPedidos().add(pedidoCompleto);
			entregaRepository.save(entrega);
		}

		return pedidoCompleto;
	}

	public List<Pedido> listarTodos(){
		return repository.findAll();
	}

}
