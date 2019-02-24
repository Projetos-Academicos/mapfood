package com.codenation.desafiofinal.service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codenation.desafiofinal.config.GoogleMapsConfig;
import com.codenation.desafiofinal.model.InformacaoRota;
import com.codenation.desafiofinal.model.Localizacao;
import com.codenation.desafiofinal.model.Rota;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.LatLng;

@Service
public class GoogleMapService {

	@Autowired
	private GoogleMapsConfig googleMapsConfig;

	public List<Rota> buscarRotas(Localizacao origem, Localizacao destino) {
		return buscarRotas(origem, destino, new ArrayList<>());
	}

	public List<Rota> buscarRotas(Localizacao origem, Localizacao destino, List<Localizacao> listaPontosParadas) {
		LatLng localizacaoOrigem = converterLocalizacaoPraLatLng(origem);
		LatLng localizacaoDestino = converterLocalizacaoPraLatLng(destino);
		List<LatLng> listaLocalizacoesPontosParadas = listaPontosParadas.stream().map(this::converterLocalizacaoPraLatLng).collect(Collectors.toList());

		DirectionsResult resultado = googleMapsConfig.getDirections(Instant.now(),
				true, localizacaoOrigem, localizacaoDestino,
				listaLocalizacoesPontosParadas.toArray(new LatLng[listaLocalizacoesPontosParadas.size()]));

		return Arrays.stream(resultado.routes)
				.map(Rota::new)
				.collect(Collectors.toList());
	}

	private LatLng converterLocalizacaoPraLatLng(Localizacao localizacao) {
		return new LatLng( Double.parseDouble(localizacao.getLatitude()), Double.parseDouble(localizacao.getLongitude()));
	}

	public InformacaoRota estimarTempoEDistancia(Localizacao localizacaoEstabelecimento, List<Localizacao> listaLocalizacao) {
		return null;
	}
}
