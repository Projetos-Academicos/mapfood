package com.codenation.desafiofinal.service;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.codenation.desafiofinal.model.Cidade;
import com.codenation.desafiofinal.model.Cliente;
import com.codenation.desafiofinal.model.Estabelecimento;
import com.codenation.desafiofinal.model.Motoboy;
import com.codenation.desafiofinal.model.Produto;
import com.codenation.desafiofinal.repository.CidadeRepository;
import com.codenation.desafiofinal.repository.ClienteRepository;
import com.codenation.desafiofinal.repository.EstabelecimentoRepository;
import com.codenation.desafiofinal.repository.MotoboyRepository;
import com.codenation.desafiofinal.repository.ProdutoRepository;

@Service
public class PopularBancoService {

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private MotoboyRepository motoboyRepository;

	@Autowired
	private CidadeRepository cidadeRepository;

	@Autowired
	private EstabelecimentoRepository estabelecimentoRepository;

	@Autowired
	private ProdutoRepository produtoRepository;

	public List<String> listaIdEstabelecimento = new ArrayList<String>();

	public void popularTabelaClienteApartirDoClienteCSV() {

		try {
			BufferedReader clienteCSV = new BufferedReader(
					new InputStreamReader(new FileInputStream("../desafio-final/src/main/resources/csv/clientes.csv")));
			String linhaArquivo;
			Scanner lerArquivo = new Scanner(clienteCSV);
			lerArquivo.nextLine();

			while (lerArquivo.hasNext()) {
				linhaArquivo = lerArquivo.nextLine();
				String[] clienteAux = linhaArquivo.split(",");

				Cliente cliente = new Cliente(Long.parseLong(clienteAux[0]), clienteAux[1], clienteAux[2]);

				clienteRepository.save(cliente);
			}
			lerArquivo.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Transactional(rollbackFor=Exception.class)
	public void popularTabelaEstabelecimentoApartirDoEstabelecimentoCSV() {

		try {
			BufferedReader estabelecimentoCSV = new BufferedReader(
					new InputStreamReader(new FileInputStream("../desafio-final/src/main/resources/csv/estabelecimentos.csv")));
			String linhaArquivo;
			Scanner lerArquivo = new Scanner(estabelecimentoCSV);
			lerArquivo.nextLine();

			while (lerArquivo.hasNext()) {
				linhaArquivo = lerArquivo.nextLine();
				String[] estabelecimentoAux = linhaArquivo.split(",");

				listaIdEstabelecimento.add(estabelecimentoAux[0]);
				Cidade cidade = cidadeRepository.findByNome(estabelecimentoAux[2]);
				if(cidade == null) {
					cidade = cidadeRepository.save(new Cidade(estabelecimentoAux[2]));
				}


				Estabelecimento estabelecimento = new Estabelecimento(estabelecimentoAux[1], cidade, estabelecimentoAux[3], estabelecimentoAux[4], estabelecimentoAux[5]);

				estabelecimentoRepository.save(estabelecimento);
			}
			lerArquivo.close();
			popularTabelaProdutoApartirDoProdutoCSV();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void popularTabelaMotoboyApartirDoMotoboyCSV() {

		try {
			BufferedReader motoboyCSV = new BufferedReader(
					new InputStreamReader(new FileInputStream("../desafio-final/src/main/resources/csv/motoboys.csv")));
			String linhaArquivo;
			Scanner lerArquivo = new Scanner(motoboyCSV);
			lerArquivo.nextLine();

			while (lerArquivo.hasNext()) {
				linhaArquivo = lerArquivo.nextLine();
				String[] motoboyAux = linhaArquivo.split(",");

				Motoboy motoboy = new Motoboy(Long.parseLong(motoboyAux[0]), motoboyAux[1], motoboyAux[2]);

				motoboyRepository.save(motoboy);
			}
			lerArquivo.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Transactional(rollbackFor=Exception.class)
	public void popularTabelaProdutoApartirDoProdutoCSV() {

		try {
			BufferedReader produtoCSV = new BufferedReader(
					new InputStreamReader(new FileInputStream("../desafio-final/src/main/resources/csv/produtos.csv")));
			String linhaArquivo;
			Scanner lerArquivo = new Scanner(produtoCSV);
			lerArquivo.nextLine();
			System.out.println(lerArquivo.hasNext());
			lerArquivo.nextLine();

			while (lerArquivo.hasNext()) {
				linhaArquivo = lerArquivo.nextLine();
				String[] produtoAux = linhaArquivo.split(",");

				Random random = new Random();
				Integer value = random.nextInt(listaIdEstabelecimento.size());

				Long idRandomico = value.longValue();
				Cidade cidade = new Cidade();

				Integer count = 0;
				if(listaIdEstabelecimento.contains(produtoAux[2])) {
					for (String est : listaIdEstabelecimento) {
						count ++;
						if(est.equals(produtoAux[2])) {
							idRandomico = count.longValue();
						}
					}
				}

				List<Cidade> listaCidadesCadastradas = cidadeRepository.findAll();
				for (Cidade cidadeAux : listaCidadesCadastradas) {
					if(cidadeAux.getNome().equals(produtoAux[6])) {
						cidade = cidadeAux;
					}
				}

				Produto produto = new Produto(produtoAux[0], idRandomico, produtoAux[4], Double.parseDouble(produtoAux[5]), cidade);

				//				List<Estabelecimento> t = estabelecimentoRepository.findAll();
				produtoRepository.save(produto);

			}
			lerArquivo.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
