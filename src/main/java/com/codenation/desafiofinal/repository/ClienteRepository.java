package com.codenation.desafiofinal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codenation.desafiofinal.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long>{

}
