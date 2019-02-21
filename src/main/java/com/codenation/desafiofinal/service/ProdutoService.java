package com.codenation.desafiofinal.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codenation.desafiofinal.model.Produto;
import com.codenation.desafiofinal.repository.ProdutoRepository;

@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository repository;

	public List<Produto> listarProdutoPorEstabelecimento(Long idEstabelecimento) {
		return repository.findByEstabelecimentoId(idEstabelecimento);
	}

	public List<Produto> listarProdutosLikeDescricao(String descricao){
		return repository.findByDescricaoContainingIgnoreCase(descricao);
	}

	public List<Produto> listarTodos() {
		return repository.findAll();
	}
}
