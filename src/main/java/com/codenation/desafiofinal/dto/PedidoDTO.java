package com.codenation.desafiofinal.dto;

import java.util.Date;
import java.util.List;

import com.codenation.desafiofinal.model.ItemPedido;

/**
 * DTO para montar o json da resposta ao criar um pedido.
 * @author Ricardo Lima
 *
 */
public class PedidoDTO {

	private Long id;
	private String nomeCliente;
	private List<ItemPedido> listaItensPedido;
	private String nomeEstabelecimento;
	private Date dataPedido;
	private Long idEntregaDoPedido;


	public Date getDataPedido() {
		return dataPedido;
	}
	public Long getId() {
		return id;
	}
	public Long getIdEntregaDoPedido() {
		return idEntregaDoPedido;
	}
	public List<ItemPedido> getListaItensPedido() {
		return listaItensPedido;
	}
	public String getNomeCliente() {
		return nomeCliente;
	}
	public String getNomeEstabelecimento() {
		return nomeEstabelecimento;
	}
	public void setDataPedido(Date dataPedido) {
		this.dataPedido = dataPedido;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setIdEntregaDoPedido(Long idEntregaDoPedido) {
		this.idEntregaDoPedido = idEntregaDoPedido;
	}
	public void setListaItensPedido(List<ItemPedido> listaItensPedido) {
		this.listaItensPedido = listaItensPedido;
	}
	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}
	public void setNomeEstabelecimento(String nomeEstabelecimento) {
		this.nomeEstabelecimento = nomeEstabelecimento;
	}
}
