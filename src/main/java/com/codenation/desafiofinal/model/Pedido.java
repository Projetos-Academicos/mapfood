package com.codenation.desafiofinal.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "pedido")
public class Pedido {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_pedido")
	@SequenceGenerator(name = "seq_pedido", sequenceName = "seq_pedido", allocationSize = 1)
	@Column(name = "id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cliente_id")
	private Cliente cliente;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "pedido_produto", joinColumns = {@JoinColumn(name = "pedido_id") }, inverseJoinColumns = {@JoinColumn(name = "produto_id") })
	private List<Produto> listaProdutos;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "estabelecimento_id")
	private Estabelecimento estabelecimento;

	public Cliente getCliente() {
		return cliente;
	}
	public Estabelecimento getEstabelecimento() {
		return estabelecimento;
	}
	public Long getId() {
		return id;
	}
	public List<Produto> getListaProdutos() {
		return listaProdutos;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	public void setEstabelecimento(Estabelecimento estabelecimento) {
		this.estabelecimento = estabelecimento;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setListaProdutos(List<Produto> listaProdutos) {
		this.listaProdutos = listaProdutos;
	}
}
