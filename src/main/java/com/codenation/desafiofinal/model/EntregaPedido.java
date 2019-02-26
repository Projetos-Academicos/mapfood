package com.codenation.desafiofinal.model;

import java.util.Date;
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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

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

	@NumberFormat(pattern = "###.##")
	@Column(name = "distancia_percorrida")
	private Double distanciaPercorrida;

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_inicio_entrega")
	private Date dataIniciandoEntrega;

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_final_entrega")
	private Date dataFinalizandoEntrega;

	public EntregaPedido() {

	}

	public EntregaPedido(Estabelecimento estabelecimento, String statusEntrega, Motoboy motoboy) {
		setEstabelecimento(estabelecimento);
		setStatusEntrega(statusEntrega);
		setMotoboy(motoboy);
	}

	public Date getDataFinalizandoEntrega() {
		return dataFinalizandoEntrega;
	}
	public Date getDataIniciandoEntrega() {
		return dataIniciandoEntrega;
	}
	public Double getDistanciaPercorrida() {
		return distanciaPercorrida;
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

	public void setDataFinalizandoEntrega(Date dataFinalizandoEntrega) {
		this.dataFinalizandoEntrega = dataFinalizandoEntrega;
	}

	public void setDataIniciandoEntrega(Date dataIniciandoEntrega) {
		this.dataIniciandoEntrega = dataIniciandoEntrega;
	}

	public void setDistanciaPercorrida(Double distanciaPercorrida) {
		this.distanciaPercorrida = distanciaPercorrida;
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
