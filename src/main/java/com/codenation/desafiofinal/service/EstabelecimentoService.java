package com.codenation.desafiofinal.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codenation.desafiofinal.model.Estabelecimento;
import com.codenation.desafiofinal.repository.EstabelecimentoRepository;

@Service
public class EstabelecimentoService {

	@Autowired
	private EstabelecimentoRepository repository;

	public List<Estabelecimento> listarTodos(){
		return repository.findAll();
	}

}
