package com.codenation.desafiofinal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codenation.desafiofinal.exception.ResourceNotFoundException;
import com.codenation.desafiofinal.exception.RotaException;
import com.codenation.desafiofinal.model.Rota;
import com.codenation.desafiofinal.service.RotaService;

@RestController
@RequestMapping("/rotas")
public class RotaController {

	@Autowired
	private RotaService service;

	@GetMapping("/motoboy-estabelecimento/{idEntrega}")
	public Rota buscarRotaEntreMotoboyEstabelecimento(@PathVariable Long idEntrega) {
		try {

			return service.definirRotaMotoboyEstabelecimento(idEntrega);

		} catch (ResourceNotFoundException | RotaException e) {
			e.printStackTrace();
		}

		return null;
	}
}
