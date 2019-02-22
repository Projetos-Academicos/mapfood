package com.codenation.desafiofinal.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "item_pedido")
public class ItemPedido {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_item_pedido")
	@SequenceGenerator(name = "seq_item_pedido", sequenceName = "seq_item_pedido", allocationSize = 1)
	@Column(name = "id")
	private Long id;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "produto_id")
	private Produto produto;

	@NotNull
	@Column(name = "quantidade")
	private Integer quantidade;

	public Long getId() {
		return id;
	}
	public Produto getProduto() {
		return produto;
	}
	public Integer getQuantidade() {
		return quantidade;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setProduto(Produto produto) {
		this.produto = produto;
	}
	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

}
