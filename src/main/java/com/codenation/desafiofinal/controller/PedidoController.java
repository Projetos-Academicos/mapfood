package com.codenation.desafiofinal.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
	public Pedido cadastro(@Valid @RequestBody Pedido pedido) {
		try {

			return service.cadastrarPedido(pedido);

		} catch (ResourceNotFoundException e) {
			e.printStackTrace();
		}

		return null;
	}

	@GetMapping
	public List<Pedido> listarTodos(){
		return service.listarTodos();
	}

	@GetMapping("/statusReport/{idPedido}")
	public void responderStatusPedido(@PathVariable Long idPedido, @RequestParam boolean pedidoAceito) {
		try {
			service.statusReportByRestaurante(idPedido, pedidoAceito);
		} catch (PedidoException e) {
			e.printStackTrace();
		}
	}

}
