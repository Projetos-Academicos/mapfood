package com.codenation.desafiofinal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codenation.desafiofinal.model.Estabelecimento;
import com.codenation.desafiofinal.service.EstabelecimentoService;

@RestController
@RequestMapping("/estabelecimentos")
public class EstabelecimentoController {

	@Autowired
	private EstabelecimentoService service;

	@GetMapping
	public List<Estabelecimento> listarTodos(){
		return service.listarTodos();
	}

}
