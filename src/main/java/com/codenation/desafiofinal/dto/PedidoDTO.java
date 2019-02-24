package com.codenation.desafiofinal.dto;

import java.util.Date;
import java.util.List;

/**
 * DTO para montar o json da resposta ao criar um pedido.
 * @author Ricardo Lima
 *
 */
public class PedidoDTO {

	private Long id;
	private Long idCliente;
	private List<Long> listaIdProduto;
	private String nomeEstabelecimento;
	private Date dataPedido;
	private Long idEntregaDoPedido;
	private String status;

	public PedidoDTO(Long id, Long idCliente, List<Long> listaIdProduto, String nomeEstabelecimento, Date dataPedido,
			Long idEntregaDoPedido, String status) {
		this.id = id;
		this.idCliente = idCliente;
		this.listaIdProduto = listaIdProduto;
		this.nomeEstabelecimento = nomeEstabelecimento;
		this.dataPedido = dataPedido;
		this.idEntregaDoPedido = idEntregaDoPedido;
		this.status = status;
	}
	public Date getDataPedido() {
		return dataPedido;
	}
	public Long getId() {
		return id;
	}
	public Long getIdCliente() {
		return idCliente;
	}
	public Long getIdEntregaDoPedido() {
		return idEntregaDoPedido;
	}
	public List<Long> getListaIdProduto() {
		return listaIdProduto;
	}
	public String getNomeEstabelecimento() {
		return nomeEstabelecimento;
	}
	public String getStatus() {
		return status;
	}
	public void setDataPedido(Date dataPedido) {
		this.dataPedido = dataPedido;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setIdCliente(Long idCliente) {
		this.idCliente = idCliente;
	}
	public void setIdEntregaDoPedido(Long idEntregaDoPedido) {
		this.idEntregaDoPedido = idEntregaDoPedido;
	}

	public void setListaIdProduto(List<Long> listaIdProduto) {
		this.listaIdProduto = listaIdProduto;
	}
	public void setNomeEstabelecimento(String nomeEstabelecimento) {
		this.nomeEstabelecimento = nomeEstabelecimento;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
