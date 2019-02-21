package com.codenation.desafiofinal.model;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "estabelecimento")
public class Estabelecimento {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_estabelecimento")
	@SequenceGenerator(name = "seq_estabelecimento", sequenceName = "seq_estabelecimento", allocationSize = 1)
	@Column(name = "id")
	private Long id;

	@Embedded
	private Localizacao localizacao;

	@Column(name = "nome")
	private String nome;

	@Column(name = "descricao")
	private String descricao;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cidade_id")
	private Cidade cidade;

	@Column(name = "hash_id")
	private String hashId;

	public Estabelecimento() {

	}

	public Estabelecimento(Long id) {
		setId(id);
	}

	public Estabelecimento(String nome, Cidade cidade, String longitude, String latitude, String descricao, String hashId) {
		setNome(nome);
		setDescricao(descricao);
		setCidade(cidade);
		setLocalizacao(new Localizacao(longitude, latitude));
		setHashId(hashId);
	}

	public Cidade getCidade() {
		return cidade;
	}
	public String getDescricao() {
		return descricao;
	}
	public String getHashId() {
		return hashId;
	}
	public Long getId() {
		return id;
	}

	public Localizacao getLocalizacao() {
		return localizacao;
	}

	public String getNome() {
		return nome;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public void setHashId(String hashId) {
		this.hashId = hashId;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setLocalizacao(Localizacao localizacao) {
		this.localizacao = localizacao;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
}
