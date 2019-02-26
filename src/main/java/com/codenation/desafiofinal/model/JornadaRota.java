package com.codenation.desafiofinal.model;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.maps.model.DirectionsLeg;

public class JornadaRota {

	private Long distancia;
	private Long duracao;

	@JsonProperty("siga_as_instrucoes")
	private List<InstrucaoRota> passosASeguir;

	public JornadaRota(DirectionsLeg leg) {
		distancia = leg.distance.inMeters;
		duracao = leg.duration.inSeconds;
		passosASeguir = Arrays.stream(leg.steps)
				.map(InstrucaoRota::new)
				.collect(Collectors.toList());
	}

	public Long getDistancia() {
		return distancia;
	}

	public Long getDuracao() {
		return duracao;
	}

	public List<InstrucaoRota> getPassosASeguir() {
		return passosASeguir;
	}

	public void setDistancia(Long distancia) {
		this.distancia = distancia;
	}

	public void setDuracao(Long duracao) {
		this.duracao = duracao;
	}

	public void setPassosASeguir(List<InstrucaoRota> passosASeguir) {
		this.passosASeguir = passosASeguir;
	}

}
