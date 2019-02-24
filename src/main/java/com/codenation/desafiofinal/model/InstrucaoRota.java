package com.codenation.desafiofinal.model;

import com.google.maps.model.DirectionsStep;

public class InstrucaoRota {

	private Long distancia;
	private Long duracao;
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
