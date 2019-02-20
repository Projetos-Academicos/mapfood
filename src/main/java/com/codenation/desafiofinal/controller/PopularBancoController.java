package com.codenation.desafiofinal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codenation.desafiofinal.service.PopularBancoService;

@RestController
@RequestMapping("/popular-banco")
public class PopularBancoController {

	@Autowired
	private PopularBancoService popularBanco;

	@GetMapping
	public void popularBanco() {
		popularBanco.popularTabelaEstabelecimentoApartirDoEstabelecimentoCSV();
	}

}
