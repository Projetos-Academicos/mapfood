package com.codenation.desafiofinal.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import com.google.maps.model.DirectionsStep;

@Embeddable
public class InstrucaoRota {

	@Column(name = "distancia")
	private Long distancia;

	@Column(name = "duracao")
	private Long duracao;

	@Column(name = "instrucao" )
	private String instrucao;

	public InstrucaoRota(DirectionsStep step) {
		setDistancia(step.distance.inMeters);
		setDuracao(step.duration.inSeconds);
		setInstrucao(step.htmlInstructions);
	}

	public Long getDistancia() {
		return distancia;
	}

	public Long getDuracao() {
		return duracao;
	}

	public String getInstrucao() {
		return instrucao;
	}

	public void setDistancia(Long distancia) {
		this.distancia = distancia;
	}

	public void setDuracao(Long duracao) {
		this.duracao = duracao;
	}

	public void setInstrucao(String instrucao) {
		this.instrucao = instrucao;
	}

}
