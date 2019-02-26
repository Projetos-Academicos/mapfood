package com.codenation.desafiofinal.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.codenation.desafiofinal.constante.ConstantesStatus;
import com.codenation.desafiofinal.dto.PedidoDTO;
import com.codenation.desafiofinal.exception.PedidoException;
import com.codenation.desafiofinal.exception.ResourceNotFoundException;
import com.codenation.desafiofinal.model.Cliente;
import com.codenation.desafiofinal.model.EntregaPedido;
import com.codenation.desafiofinal.model.Estabelecimento;
import com.codenation.desafiofinal.model.ItemPedido;
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

	public Motoboy buscarMotoboyDisponivelMaisProximo(Localizacao localizacaoEstabelecimento) {

		List<Motoboy> listaMotoboys = motoboyRepository.findAll();
		List<Localizacao> listaLocalizacao = obterLocalizacaoDosMotoboys(listaMotoboys);

		Double latitudeMotoboy, menorLatitude = null;
		Double longitudeMotoboy, menorLongitude = null;
		Double distanciaMotoboy;
		Double menorDistancia = Double.MAX_VALUE;

		for (Localizacao localizacao : listaLocalizacao) {

			latitudeMotoboy = Double.parseDouble(localizacao.getLatitude());
			longitudeMotoboy = Double.parseDouble(localizacao.getLongitude());
			distanciaMotoboy = MapFoodUtil.calcularDistanciaEntreDoisPontos(Double.parseDouble(localizacaoEstabelecimento.getLatitude()),Double.parseDouble(localizacaoEstabelecimento.getLongitude()), latitudeMotoboy, longitudeMotoboy);

			if(menorDistancia > distanciaMotoboy) {
				menorLatitude = latitudeMotoboy;
				menorLongitude = longitudeMotoboy;
				menorDistancia = distanciaMotoboy;
			}
		}

		//		if(menorDistancia > 20d) { //TODO VALIDAÇÃO DE DISTANCIA MAXIMA ENTRE MOTOBOY E ESTABELECIMENTO, RETIRAR SOMENTE NO AMBIENTE DE TESTE
		//			return null;
		//		}

		return motoboyRepository.findByLocalizacaoLatitudeAndLocalizacaoLongitude(menorLatitude.toString(), menorLongitude.toString());
	}

	@Transactional(readOnly = false)
	public Pedido cadastrarPedido(Pedido pedido) throws ResourceNotFoundException, PedidoException {

		Pedido pedidoCompleto = completarInformacoesDoPedido(pedido);
		pedidoCompleto = determinarSeOPedidoEntraEmUmaEntregaExistente(pedidoCompleto);

		Motoboy motoboySelecionado = buscarMotoboyDisponivelMaisProximo(pedidoCompleto.getEstabelecimento().getLocalizacao());

		if(!pedidoCompleto.isPedidoIncluidoEmUmaEntrega()) {

			if(motoboySelecionado == null) {
				throw new PedidoException("Não foi possível concluir o pedido por falta de motoboy disponível na sua região, tente novamente mais tarde! ");
			}else {
				EntregaPedido entrega = new EntregaPedido(pedidoCompleto.getEstabelecimento(), ConstantesStatus.AGUARDANDO_RESPOSTA, motoboySelecionado);
				entrega = entregaRepository.save(entrega);
				pedidoCompleto.setEntrega(entrega);
			}

		}

		return repository.save(pedidoCompleto);
	}

	public PedidoDTO cadastrarPedidoCliente(Pedido pedido) throws ResourceNotFoundException, PedidoException {
		Pedido pedidoSalvo = cadastrarPedido(pedido);
		List<Long> listaIdProduto = new ArrayList<Long>();
		for (ItemPedido item : pedido.getListaItemPedido()) {
			listaIdProduto.add(item.getProduto().getId());
		}
		PedidoDTO pedidoResponse = new PedidoDTO(pedidoSalvo.getId(), pedidoSalvo.getCliente().getId(),listaIdProduto,
				pedidoSalvo.getEstabelecimento().getNome(), pedidoSalvo.getDataRealizacaoPedido(), pedidoSalvo.getEntrega().getId(), pedidoSalvo.getStatus());
		return pedidoResponse;
	}

	public Double calcularDistanciaEntreDoisPontos(Localizacao localizacao1, Localizacao localizacao2) {

		Double latitude1 = Double.parseDouble(localizacao1.getLatitude());
		Double longitude1 = Double.parseDouble(localizacao1.getLongitude());
		Double latitude2 = Double.parseDouble(localizacao2.getLatitude());
		Double longitude2 = Double.parseDouble(localizacao2.getLongitude());
		Double distanciaCalculada = MapFoodUtil.calcularDistanciaEntreDoisPontos(latitude1, longitude1, latitude2, longitude2);

		return distanciaCalculada;
	}

	public Pedido completarInformacoesDoPedido(Pedido pedido) throws ResourceNotFoundException {

		pedido.setStatus(ConstantesStatus.AGUARDANDO_RESPOSTA);
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

		List<EntregaPedido> listaEntregasMesmoEstabelecimento = entregaRepository.findByEstabelecimentoIdAndStatusEntrega(pedidoCompleto.getEstabelecimento().getId(), ConstantesStatus.AGUARDANDO_RESPOSTA);

		for (EntregaPedido entrega : listaEntregasMesmoEstabelecimento) {
			if(!CollectionUtils.isEmpty(entrega.getListaPedidos()) && entrega.getListaPedidos().size() < 5) {
				for (Pedido pd : entrega.getListaPedidos()) {

					if(pedidoCompleto.getEntrega() != null ) {
						break;
					}

					Double distanciaCalculada = calcularDistanciaEntreDoisPontos(pedidoCompleto.getCliente().getLocalizacao(), pd.getCliente().getLocalizacao());

					if(MapFoodUtil.getMinutosFromTimes(pd.getDataRealizacaoPedido(), pedidoCompleto.getDataRealizacaoPedido()) <= 2 && distanciaCalculada <= 5d) {
						pedidoCompleto.setPedidoIncluidoEmUmaEntrega(true);
						pedidoCompleto.setEntrega(entrega);
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
	public void finalizarEntrega(Long idEntrega) throws ResourceNotFoundException {
		EntregaPedido entrega = entregaRepository.findById(idEntrega).orElseThrow(() -> new ResourceNotFoundException("Entrega não encontrada ::" + idEntrega));

		entrega.setDataFinalizandoEntrega(MapFoodUtil.getDataAtual());
		entrega.setStatusEntrega(ConstantesStatus.FINALIZADO);

		entregaRepository.save(entrega);
	}

	public void informarEntregaPedido(Long id) throws ResourceNotFoundException {
		Pedido pedido = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado ::" + id));

		pedido.setDataFinalizacaoPedido(MapFoodUtil.getDataAtual());

		boolean isFinalizarPedido = isUltimoPedidoDaEntrega(pedido.getEntrega().getId());

		if(isFinalizarPedido) {
			finalizarEntrega(pedido.getEntrega().getId());
		}

		pedido.setStatus(ConstantesStatus.FINALIZADO);
		repository.save(pedido);
	}

	public boolean isUltimoPedidoDaEntrega(Long idEntrega) {
		Long count = repository.countByEntregaIdAndStatusNot(idEntrega, ConstantesStatus.FINALIZADO);

		if(count > 1) {
			return false;
		}

		return true;
	}

	public List<Pedido> listarTodos(){
		return repository.findAll();
	}

	public List<Localizacao> obterLocalizacaoDosMotoboys(List<Motoboy> listaMotoboys) {

		List<Localizacao> listaLocalizacao = new ArrayList<Localizacao>();
		listaMotoboys.forEach(motoboy -> {
			Long countEntregasEmAbertas = entregaRepository.countByMotoboyIdAndStatusEntrega(motoboy.getId(), ConstantesStatus.EM_ANDAMENTO);

			if(!(countEntregasEmAbertas > 0)) {
				Localizacao localizacao = new Localizacao();
				localizacao.setLatitude(motoboy.getLocalizacao().getLatitude());
				localizacao.setLongitude(motoboy.getLocalizacao().getLongitude());
				listaLocalizacao.add(localizacao);
			}
		});

		return listaLocalizacao;
	}

}
