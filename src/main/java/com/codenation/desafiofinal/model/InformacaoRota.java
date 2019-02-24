package com.codenation.desafiofinal.model;

import java.util.List;

import com.google.maps.model.Distance;
import com.google.maps.model.Duration;
import com.google.maps.model.LatLng;

public class InformacaoRota {

	private LatLng localizacaoEstabelecimento;
	private List<LatLng> listaLocalizacao;
	private Duration duracao;
	private Distance distancia;


	public Distance getDistancia() {
		return distancia;
	}
	public Duration getDuracao() {
		return duracao;
	}
	public List<LatLng> getListaLocalizacao() {
		return listaLocalizacao;
	}
	public void setDistancia(Distance distancia) {
		this.distancia = distancia;
	}
	public void setDuracao(Duration duracao) {
		this.duracao = duracao;
	}
	public void setListaLocalizacao(List<LatLng> listaLocalizacao) {
		this.listaLocalizacao = listaLocalizacao;
	}
	public LatLng getLocalizacaoEstabelecimento() {
		return localizacaoEstabelecimento;
	}
	public void setLocalizacaoEstabelecimento(LatLng localizacaoEstabelecimento) {
		this.localizacaoEstabelecimento = localizacaoEstabelecimento;
	}

}
