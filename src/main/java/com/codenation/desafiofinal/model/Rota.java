package com.codenation.desafiofinal.model;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.maps.model.DirectionsRoute;

public class Rota {

	@JsonProperty("rota")
	private List<JornadaRota> listaJornada;

	public Rota(DirectionsRoute rota) {
		setListaJornada(Arrays.stream(rota.legs)
				.map(JornadaRota::new)
				.collect(Collectors.toList()));
	}

	public List<JornadaRota> getListaJornada() {
		return listaJornada;
	}

	public void setListaJornada(List<JornadaRota> listaJornada) {
		this.listaJornada = listaJornada;
	}



}
