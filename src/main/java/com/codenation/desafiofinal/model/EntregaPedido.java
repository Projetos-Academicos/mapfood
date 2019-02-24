package com.codenation.desafiofinal.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name = "entrega")
public class EntregaPedido {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_entrega")
	@SequenceGenerator(name = "seq_entrega", sequenceName = "seq_entrega", allocationSize = 1)
	@Column(name = "id")
	private Long id;

	@Size(max = 5)
	@OneToMany(mappedBy = "entrega")
	private List<Pedido> listaPedidos;

	@Size(max = 5)
	@ManyToMany
	@JoinTable(name = "rel_entrega_cliente", joinColumns = {@JoinColumn(name = "entrega_id") }, inverseJoinColumns = {@JoinColumn(name = "cliente_id") })
	private List<Cliente> listaClientes;

	@ManyToOne
	@JoinColumn(name = "motoboy_id")
	private Motoboy motoboy;

	@ManyToOne
	@JoinColumn(name = "estabelecimento_id")
	private Estabelecimento estabelecimento;

	@Column(name = "status")
	private String statusEntrega;

	@Embedded
	private DadosRota dadosRota;

	public EntregaPedido() {

	}

	public EntregaPedido(Estabelecimento estabelecimento, String statusEntrega, Motoboy motoboy) {
		setEstabelecimento(estabelecimento);
		setStatusEntrega(statusEntrega);
		setMotoboy(motoboy);
	}

	public DadosRota getDadosRota() {
		return dadosRota;
	}
	public Estabelecimento getEstabelecimento() {
		return estabelecimento;
	}
	public Long getId() {
		return id;
	}
	public List<Cliente> getListaClientes() {
		return listaClientes;
	}
	public List<Pedido> getListaPedidos() {
		return listaPedidos;
	}
	public Motoboy getMotoboy() {
		return motoboy;
	}
	public String getStatusEntrega() {
		return statusEntrega;
	}
	public void setDadosRota(DadosRota dadosRota) {
		this.dadosRota = dadosRota;
	}
	public void setEstabelecimento(Estabelecimento estabelecimento) {
		this.estabelecimento = estabelecimento;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public void setListaClientes(List<Cliente> listaClientes) {
		this.listaClientes = listaClientes;
	}

	public void setListaPedidos(List<Pedido> listaPedidos) {
		this.listaPedidos = listaPedidos;
	}

	public void setMotoboy(Motoboy motoboy) {
		this.motoboy = motoboy;
	}

	public void setStatusEntrega(String statusEntrega) {
		this.statusEntrega = statusEntrega;
	}
}
