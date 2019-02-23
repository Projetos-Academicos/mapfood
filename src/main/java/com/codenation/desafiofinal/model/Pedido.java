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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

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
	@JoinTable(name = "rel_pedido_item_pedido", joinColumns = {@JoinColumn(name = "pedido_id") }, inverseJoinColumns = {@JoinColumn(name = "item_pedido_id") })
	private List<ItemPedido> listaItemPedido;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "estabelecimento_id")
	private Estabelecimento estabelecimento;

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_realizacao_pedido")
	private Date dataRealizacaoPedido;

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_finalizacao_pedido")
	private Date dataFinalizacaoPedido;

	@Column(name = "valor_total")
	private Double valorTotal;

	@Column(name = "status")
	private StatusEnum status;

	@Transient
	private boolean isPedidoIncluidoEmUmaEntrega;

	//TODO INCLUIR ATRIBUTO DA ROTA

	public Cliente getCliente() {
		return cliente;
	}
	public Date getDataFinalizacaoPedido() {
		return dataFinalizacaoPedido;
	}
	public Date getDataRealizacaoPedido() {
		return dataRealizacaoPedido;
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
	public boolean isPedidoIncluidoEmUmaEntrega() {
		return isPedidoIncluidoEmUmaEntrega;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	public void setDataFinalizacaoPedido(Date dataFinalizacaoPedido) {
		this.dataFinalizacaoPedido = dataFinalizacaoPedido;
	}
	public void setDataRealizacaoPedido(Date dataRealizacaoPedido) {
		this.dataRealizacaoPedido = dataRealizacaoPedido;
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
	public void setPedidoIncluidoEmUmaEntrega(boolean isPedidoIncluidoEmUmaEntrega) {
		this.isPedidoIncluidoEmUmaEntrega = isPedidoIncluidoEmUmaEntrega;
	}
	public void setStatus(StatusEnum status) {
		this.status = status;
	}
	public void setValorTotal(Double valorTotal) {
		this.valorTotal = valorTotal;
	}
}
