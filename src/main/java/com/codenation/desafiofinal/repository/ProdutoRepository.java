package com.codenation.desafiofinal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codenation.desafiofinal.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

}
