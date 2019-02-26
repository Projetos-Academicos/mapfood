package com.codenation.desafiofinal.dto;

public class RelatorioEntregaDTO {

	private String entrega;
	private String motoboy;
	private String tempoGasto;
	private String quilometragem;


	public String getEntrega() {
		return entrega;
	}
	public String getMotoboy() {
		return motoboy;
	}
	public String getQuilometragem() {
		return quilometragem;
	}
	public String getTempoGasto() {
		return tempoGasto;
	}
	public void setEntrega(String entrega) {
		this.entrega = entrega;
	}
	public void setMotoboy(String motoboy) {
		this.motoboy = motoboy;
	}
	public void setQuilometragem(String quilometragem) {
		this.quilometragem = quilometragem;
	}
	public void setTempoGasto(String tempoGasto) {
		this.tempoGasto = tempoGasto;
	}
}
