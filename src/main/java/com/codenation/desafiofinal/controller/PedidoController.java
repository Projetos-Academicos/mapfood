package com.codenation.desafiofinal.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codenation.desafiofinal.dto.PedidoDTO;
import com.codenation.desafiofinal.exception.PedidoException;
import com.codenation.desafiofinal.exception.ResourceNotFoundException;
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

			return service.cadastrarPedidoCliente(pedido);

		} catch (ResourceNotFoundException | PedidoException e) {
			e.printStackTrace();
		}

		return null;
	}

	@PostMapping("/finalizar-pedido/{id}")
	public void finalizarEntregaPedido(@PathVariable Long id) {
		try {

			service.informarEntregaPedido(id);

		} catch (ResourceNotFoundException e) {
			e.printStackTrace();
		}
	}

	@GetMapping
	public List<Pedido> listarTodos(){
		return service.listarTodos();
	}
}
