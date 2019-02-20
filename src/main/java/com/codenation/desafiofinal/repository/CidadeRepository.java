package com.codenation.desafiofinal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codenation.desafiofinal.model.Cidade;

public interface CidadeRepository extends JpaRepository<Cidade, Long> {
	Cidade findByNome(String nome);
}
