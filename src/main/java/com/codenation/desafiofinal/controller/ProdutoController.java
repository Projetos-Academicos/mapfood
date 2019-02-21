package com.codenation.desafiofinal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codenation.desafiofinal.model.Produto;
import com.codenation.desafiofinal.service.ProdutoService;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

	@Autowired
	private ProdutoService service;

	@GetMapping("/filtro-descricao")
	public List<Produto> listarProdutosLikeDescricao(@RequestParam String descricao){
		return service.listarProdutosLikeDescricao(descricao);
	}

	@GetMapping("/{idEstabelecimento}")
	public List<Produto> listarProdutosPorEstabelecimento(@PathVariable Long idEstabelecimento){
		return service.listarProdutoPorEstabelecimento(idEstabelecimento);
	}

	@GetMapping
	public List<Produto> listarTodos(){
		return service.listarTodos();
	}

}
