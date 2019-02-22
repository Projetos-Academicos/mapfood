package com.codenation.desafiofinal.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.codenation.desafiofinal.enums.StatusEnum;

@Entity
@Table(name = "pedido")
public class Pedido {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_pedido")
	@SequenceGenerator(name = "seq_pedido", sequenceName = "seq_pedido", allocationSize = 1)
	@Column(name = "id")
	private Long id;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "cliente_id")
	private Cliente cliente;

	@NotNull
	@ManyToMany
	@JoinTable(name = "pedido_item", joinColumns = {@JoinColumn(name = "pedido_id") }, inverseJoinColumns = {@JoinColumn(name = "item_pedido_id") })
	private List<ItemPedido> listaItemPedido;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "estabelecimento_id")
	private Estabelecimento estabelecimento;

	@Column(name = "valor_total")
	private Double valorTotal;

	@Column(name = "status")
	private StatusEnum status;

	public Cliente getCliente() {
		return cliente;
	}
	public Estabelecimento getEstabelecimento() {
		return estabelecimento;
	}
	public Long getId() {
		return id;
	}
	public List<ItemPedido> getListaItemPedido() {
		return listaItemPedido;
	}
	public StatusEnum getStatus() {
		return status;
	}
	public Double getValorTotal() {
		return valorTotal;
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
	public void setListaItemPedido(List<ItemPedido> listaItemPedido) {
		this.listaItemPedido = listaItemPedido;
	}
	public void setStatus(StatusEnum status) {
		this.status = status;
	}
	public void setValorTotal(Double valorTotal) {
		this.valorTotal = valorTotal;
	}
}
