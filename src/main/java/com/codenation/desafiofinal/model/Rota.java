package com.codenation.desafiofinal.model;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.google.maps.model.DirectionsRoute;

public class Rota {
	private List<JornadaRota> listajJornadas;

	public Rota(DirectionsRoute rota) {
		listajJornadas = Arrays.stream(rota.legs)
				.map(JornadaRota::new)
				.collect(Collectors.toList());
	}

	public List<JornadaRota> getListajJornadas() {
		return listajJornadas;
	}

	public void setListajJornadas(List<JornadaRota> listajJornadas) {
		this.listajJornadas = listajJornadas;
	}

}
