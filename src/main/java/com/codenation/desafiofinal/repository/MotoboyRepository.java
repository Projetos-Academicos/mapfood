package com.codenation.desafiofinal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codenation.desafiofinal.model.Motoboy;

public interface MotoboyRepository extends JpaRepository<Motoboy, Long>{

	// List<Motoboy> findByListaEntregasStatus //TODO IMPLEMENTAR QUERY PRA FILTRAR OS MOTOBOYS (CANDIDADOS PARA VER QUAL VAI SER SELECIONADO PARA ATENDER A ENTREGA)
	// PRA ELIMINAR OS MOTOBOYS QUE JÁ ESTÃO VINCULADO A ALGUMA ENTREGA COM STATUS EM_ANDAMENTO

	Motoboy findByLocalizacaoLatitudeAndLocalizacaoLongitude(String latitude, String longitude);

}
