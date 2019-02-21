package com.codenation.desafiofinal.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
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
@Table(name = "produto")
public class Produto {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_produto")
	@SequenceGenerator(name = "seq_produto", sequenceName = "seq_produto", allocationSize = 1)
	@Column(name = "id")
	private Long id;

	@Column(name = "descricao")
	private String descricao;

	@Column(name = "classificacao")
	private String classificacao;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "estabelecimento_id")
	private Estabelecimento estabelecimento;

	@ManyToOne(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
	@JoinColumn(name = "cidade_id")
	private Cidade cidade;

	@Column(name = "preco")
	private Double preco;

	public Produto() {

	}

	public Produto(String descricao, Long idEstabelecimento, String classificacao, Double preco, Cidade cidade) {
		setDescricao(descricao);
		setEstabelecimento(new Estabelecimento(idEstabelecimento));
		setClassificacao(classificacao);
		setPreco(preco);
		setCidade(cidade);
	}

	public Cidade getCidade() {
		return cidade;
	}
	public String getClassificacao() {
		return classificacao;
	}
	public String getDescricao() {
		return descricao;
	}
	public Estabelecimento getEstabelecimento() {
		return estabelecimento;
	}
	public Long getId() {
		return id;
	}
	public Double getPreco() {
		return preco;
	}
	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}
	public void setClassificacao(String classificacao) {
		this.classificacao = classificacao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public void setEstabelecimento(Estabelecimento estabelecimento) {
		this.estabelecimento = estabelecimento;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}

}
