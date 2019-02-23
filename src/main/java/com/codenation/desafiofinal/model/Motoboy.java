package com.codenation.desafiofinal.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name= "motoboy")
public class Motoboy {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_motoboy")
	@SequenceGenerator(name = "seq_motoboy", sequenceName = "seq_motoboy", allocationSize = 1)
	@Column(name = "id")
	private Long id;

	@Embedded
	private Localizacao localizacao;

	@OneToMany(mappedBy = "motoboy")
	private List<EntregaPedido> listaEntregas;

	public Motoboy() {

	}

	public Motoboy(Long id, String longitude, String latitude) {
		setId(id);
		setLocalizacao(new Localizacao(longitude, latitude));
	}

	public Long getId() {
		return id;
	}
	public List<EntregaPedido> getListaEntregas() {
		return listaEntregas;
	}
	public Localizacao getLocalizacao() {
		return localizacao;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public void setListaEntregas(List<EntregaPedido> listaEntregas) {
		this.listaEntregas = listaEntregas;
	}

	public void setLocalizacao(Localizacao localizacao) {
		this.localizacao = localizacao;
	}
}
