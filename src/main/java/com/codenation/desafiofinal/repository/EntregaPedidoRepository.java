package com.codenation.desafiofinal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codenation.desafiofinal.model.EntregaPedido;

public interface EntregaPedidoRepository extends JpaRepository<EntregaPedido, Long> {

	List<EntregaPedido> findByEstabelecimentoIdAndStatusEntrega(Long idEstabelecimento, String statusEntrega);
}
