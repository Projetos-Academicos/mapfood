package com.codenation.desafiofinal.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codenation.desafiofinal.enums.StatusEnum;
import com.codenation.desafiofinal.exception.PedidoException;
import com.codenation.desafiofinal.exception.ProdutoException;
import com.codenation.desafiofinal.model.EntregaPedido;
import com.codenation.desafiofinal.model.Localizacao;
import com.codenation.desafiofinal.model.Motoboy;
import com.codenation.desafiofinal.model.Pedido;
import com.codenation.desafiofinal.repository.EntregaPedidoRepository;
import com.codenation.desafiofinal.repository.ItemPedidoRepository;
import com.codenation.desafiofinal.repository.MotoboyRepository;
import com.codenation.desafiofinal.repository.PedidoRepository;
import com.codenation.desafiofinal.repository.ProdutoRepository;
import com.codenation.desafiofinal.util.MapFoodUtil;

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

	public void buscarMotoboyDisponivelMaisProximo(Localizacao localizacaoEstabelecimento) {

		List<Motoboy> listaMotoboys = motoboyRepository.findAll();
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

		System.out.print("lat = " + latMenor);
		System.out.print(" lon = " + lonMenor);
		System.out.print(" DistÃ¢ncia "+ String.format("%.2f", menor) + " Kilometers\n");
	}

	public Pedido cadastro(Pedido pedido) {

		if(pedido.getStatus() == null) {
			pedido.setStatus(StatusEnum.AGUARDANDO_RESPOSTA);
		}

		pedido.setDataRealizacaoPedido(MapFoodUtil.getDataAtual());

		List<EntregaPedido> listaEntregasMesmoEstabelecimento = entregaRepository.findByEstabelecimentoIdAndStatusEntrega(pedido.getEstabelecimento().getId(), StatusEnum.EM_ANDAMENTO);

		listaEntregasMesmoEstabelecimento.forEach(entrega -> {
			if(entrega.getListaPedidos() != null && entrega.getListaPedidos().size() < 5) {
				entrega.getListaPedidos().forEach(pd ->{

					Double lat1 = Double.parseDouble(pd.getCliente().getLocalizacao().getLatitude());
					Double lon1 = Double.parseDouble(pd.getCliente().getLocalizacao().getLongitude());
					Double lat2 = Double.parseDouble(pedido.getCliente().getLocalizacao().getLatitude());
					Double lon2 = Double.parseDouble(pedido.getCliente().getLocalizacao().getLongitude());
					Double distanciaCalculada = MapFoodUtil.calcularDistanciaEntreDoisPontos(lat1, lon1, lat2, lon2);

					if(MapFoodUtil.getMinutosFromTimes(pd.getDataRealizacaoPedido(), pedido.getDataRealizacaoPedido()) <= 2 && distanciaCalculada <= 5d) {
						entrega.getListaPedidos().add(pedido);
					}
				});
			}
		});

		pedido.setValorTotal(0d);
		pedido.getListaItemPedido().forEach(item -> {
			try {
				item.setProduto(produtoRepository.findById(item.getProduto().getId())
						.orElseThrow(() -> new ProdutoException("Produto não encontrado ::" + item.getProduto().getId())));

				if(!item.getProduto().getEstabelecimento().getId().equals(pedido.getEstabelecimento().getId())) {
					try {
						throw new ProdutoException("O produto informado não pertence ao Estabelecimento escolhido!");
					} catch (ProdutoException e) {
						e.printStackTrace();
					}
				}
				itemPedidoRepository.save(item);
				Double valorTotalItem = item.getProduto().getPreco() * item.getQuantidade();
				pedido.setValorTotal(pedido.getValorTotal() + valorTotalItem);

			} catch (ProdutoException e1) {
				e1.printStackTrace();
			}
		});




		return repository.save(pedido);
	}

	public List<Pedido> listarTodos(){
		return repository.findAll();
	}

	public void statusReportByRestaurante(Long idPedido, boolean pedidoAceito) throws PedidoException {
		Pedido pedido = repository.findById(idPedido).orElseThrow(() -> new PedidoException("Pedido não encontrado ::" + idPedido));
		pedido.setStatus(pedidoAceito ? StatusEnum.ACEITO : StatusEnum.RECUSADO);
		pedido = repository.save(pedido);

		if(pedido.getStatus().equals(StatusEnum.ACEITO)) { //VERIFICAR ESSA REGRA, O IDEAL PE JOGAR ELA LÁ PRA CIMA PRA FAZER DURANTE NO CADASTRO DO PEDIDO,
			//ASSIM QUE SALVAR O PEDIDO JÁ FAZER O ESTABELECIMENTO BUSCAR UM MOTOBOY, PRA JÁ INFORMAR O STATUS SE ACEITA OU NÃO.
			// implementar logica para procurar o motoboy mais proximo do estabelecimento, para enviar a rota de onde ele tá até o estabelecimento.
			// de acordo com a localização do estabelecimento tem que fazer uma varredura até x km pra ver se tem algum motoboy disponivel
			//caso o motoboy estaja muito longe do estabelecimento, cancelar o pedido (mudar status). (definir o quão longe tem que está pra cancelar)
			//caso ache o motoboy, mandar a rota pra ele e mudar o status pra EM_ANDAMENTO.
		}
	}

}
