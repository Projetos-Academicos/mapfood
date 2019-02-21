package com.codenation.desafiofinal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codenation.desafiofinal.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

	List<Produto> findByDescricaoContainingIgnoreCase(String descricao);
	List<Produto> findByEstabelecimentoId(Long idEstabelecimento);
}
