package com.codenation.desafiofinal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codenation.desafiofinal.model.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

	Long countByEntregaIdAndStatusNot(Long idEntrega, String status);
}
