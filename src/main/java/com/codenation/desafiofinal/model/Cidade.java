package com.codenation.desafiofinal.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "cidade")
public class Cidade {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_cidade")
	@SequenceGenerator(name = "seq_cidade", sequenceName = "seq_cidade", allocationSize = 1)
	@Column(name = "id")
	private Long id;

	@Column(name = "nome")
	private String nome;

	public Cidade(){

	}

	public Cidade(String nome){
		setNome(nome);
	}

	public Long getId() {
		return id;
	}
	public String getNome() {
		return nome;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
}
