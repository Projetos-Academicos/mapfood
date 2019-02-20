package com.codenation.desafiofinal.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Localizacao {

	@Column(name = "longitude")
	private String longitude;

	@Column(name = "latitude")
	private String latitude;

	public Localizacao() {

	}
	public Localizacao(String longitude, String latitude) {
		setLongitude(longitude);
		setLatitude(latitude);
	}

	public String getLatitude() {
		return latitude;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
}
