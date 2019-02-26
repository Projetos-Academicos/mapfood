package com.codenation.desafiofinal.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.codenation.desafiofinal.constante.ConstantesStatus;
import com.codenation.desafiofinal.dto.RelatorioEntregaDTO;
import com.codenation.desafiofinal.exception.ResourceNotFoundException;
import com.codenation.desafiofinal.model.EntregaPedido;
import com.codenation.desafiofinal.model.Estabelecimento;
import com.codenation.desafiofinal.repository.EntregaPedidoRepository;
import com.codenation.desafiofinal.repository.EstabelecimentoRepository;
import com.codenation.desafiofinal.util.MapFoodUtil;

@Service
public class EstabelecimentoService {

	@Autowired
	private EstabelecimentoRepository repository;

	@Autowired
	private EntregaPedidoRepository entregaRepository;

	public List<RelatorioEntregaDTO> gerarRelatorioEstabelecimento(Long id) throws ResourceNotFoundException {

		List<EntregaPedido> listaEntregas = entregaRepository.findByEstabelecimentoIdAndStatusEntrega(id, ConstantesStatus.FINALIZADO);
		List<RelatorioEntregaDTO> listaRelatorios = new ArrayList<RelatorioEntregaDTO>();

		if(!CollectionUtils.isEmpty(listaEntregas)) {
			listaEntregas.forEach(entrega -> {
				RelatorioEntregaDTO relatorio = new RelatorioEntregaDTO();
				relatorio.setEntrega("Id da Entrega: " + entrega.getId());
				relatorio.setMotoboy("Id do Motoboy que realizou a entrega: " + entrega.getMotoboy().getId());
				relatorio.setQuilometragem("Distancia total: " + MapFoodUtil.converterMetrosEmQuilometros(entrega.getDistanciaPercorrida()) + " Km");
				relatorio.setTempoGasto("Tempo total da realização da entrega: " + MapFoodUtil.getMinutosFromTimes(entrega.getDataIniciandoEntrega(), entrega.getDataFinalizandoEntrega()) + " Minutos");
				listaRelatorios.add(relatorio);
			});
		}

		return listaRelatorios;
	}

	public List<Estabelecimento> listarTodos(){
		return repository.findAll();
	}

}
