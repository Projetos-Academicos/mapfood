package com.codenation.desafiofinal.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class DadosRota {

	@Column(name = "quilometragem")
	private String quilometragemPercorrida;

	@Column(name = "tempo_gasto")
	private Double tempoGastoEmMinutos;

	public String getQuilometragemPercorrida() {
		return quilometragemPercorrida;
	}
	public Double getTempoGastoEmMinutos() {
		return tempoGastoEmMinutos;
	}
	public void setQuilometragemPercorrida(String quilometragemPercorrida) {
		this.quilometragemPercorrida = quilometragemPercorrida;
	}
	public void setTempoGastoEmMinutos(Double tempoGastoEmMinutos) {
		this.tempoGastoEmMinutos = tempoGastoEmMinutos;
	}
}
