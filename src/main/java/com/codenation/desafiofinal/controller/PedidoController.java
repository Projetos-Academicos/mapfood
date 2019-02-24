package com.codenation.desafiofinal.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codenation.desafiofinal.dto.PedidoDTO;
import com.codenation.desafiofinal.exception.ResourceNotFoundException;
import com.codenation.desafiofinal.model.ItemPedido;
import com.codenation.desafiofinal.model.Pedido;
import com.codenation.desafiofinal.service.PedidoService;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

	@Autowired
	private PedidoService service;

	@PostMapping
	public PedidoDTO cadastro(@Valid @RequestBody Pedido pedido) {
		try {

			Pedido pedidoSalvo = service.cadastrarPedido(pedido);
			List<Long> listaIdProduto = new ArrayList<Long>();
			for (ItemPedido item : pedido.getListaItemPedido()) {
				listaIdProduto.add(item.getProduto().getId());
			}
			PedidoDTO pedidoResponse = new PedidoDTO(pedidoSalvo.getId(), pedidoSalvo.getCliente().getId(),listaIdProduto,
					pedidoSalvo.getEstabelecimento().getNome(), pedidoSalvo.getDataRealizacaoPedido(), pedidoSalvo.getEntrega().getId(), pedidoSalvo.getStatus());
			return pedidoResponse;

		} catch (ResourceNotFoundException e) {
			e.printStackTrace();
		}

		return null;
	}

	@GetMapping
	public List<Pedido> listarTodos(){
		return service.listarTodos();
	}
}
