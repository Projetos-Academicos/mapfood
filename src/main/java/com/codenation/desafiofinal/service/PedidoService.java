package com.codenation.desafiofinal.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codenation.desafiofinal.enums.StatusEnum;
import com.codenation.desafiofinal.exception.PedidoException;
import com.codenation.desafiofinal.exception.ProdutoException;
import com.codenation.desafiofinal.model.Pedido;
import com.codenation.desafiofinal.repository.ItemPedidoRepository;
import com.codenation.desafiofinal.repository.PedidoRepository;
import com.codenation.desafiofinal.repository.ProdutoRepository;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository repository;

	@Autowired
	private ItemPedidoRepository itemPedidoRepository;

	@Autowired
	private ProdutoRepository produtoRepository;

	public Pedido cadastro(Pedido pedido) {

		if(pedido.getStatus() == null) {
			pedido.setStatus(StatusEnum.AGUARDANDO_RESPOSTA);
		}

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

		if(pedido.getStatus().equals(StatusEnum.ACEITO)) {
			// implementar logica para procurar o motoboy mais proximo do estabelecimento, para enviar a rota de onde ele tá até o estabelecimento.
			// de acordo com a localização do estabelecimento tem que fazer uma varredura até x km pra ver se tem algum motoboy disponivel
			//caso o motoboy estaja muito longe do estabelecimento, cancelar o pedido (mudar status). (definir o quão longe tem que está pra cancelar)
			//caso ache o motoboy, mandar a rota pra ele e mudar o status pra EM_ANDAMENTO.
		}

	}

}
