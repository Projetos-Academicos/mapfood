package com.codenation.desafiofinal.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.codenation.desafiofinal.enums.StatusEnum;

@Entity
@Table(name = "entrega")
public class EntregaPedido {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_entrega")
	@SequenceGenerator(name = "seq_entrega", sequenceName = "seq_entrega", allocationSize = 1)
	@Column(name = "id")
	private Long id;

	@OneToMany(mappedBy = "entrega")
	private List<Pedido> listaPedidos = new ArrayList<Pedido>(5);


	@ManyToMany
	private List<Cliente> listaClientes = new ArrayList<Cliente>(5);

	@ManyToOne
	@JoinColumn(name = "motoboy_id")
	private Motoboy motoboy;

	private Estabelecimento estabelecimento;

	private StatusEnum statusEntrega;

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
	public StatusEnum getStatusEntrega() {
		return statusEntrega;
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
	public void setStatusEntrega(StatusEnum statusEntrega) {
		this.statusEntrega = statusEntrega;
	}

}
