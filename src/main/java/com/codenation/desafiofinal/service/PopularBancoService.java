package com.codenation.desafiofinal.service;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
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

	@Transactional(rollbackFor=Exception.class)
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

				Cidade cidade = cidadeRepository.findByNome(estabelecimentoAux[2]);
				if(cidade == null) {
					if(estabelecimentoAux[2] != null && !estabelecimentoAux[2].isEmpty()) {
						cidade = cidadeRepository.save(new Cidade(estabelecimentoAux[2]));
					}
				}

				Estabelecimento estabelecimento = new Estabelecimento(estabelecimentoAux[1], cidade, estabelecimentoAux[3], estabelecimentoAux[4], estabelecimentoAux[5], estabelecimentoAux[0]);

				estabelecimentoRepository.save(estabelecimento);
			}
			lerArquivo.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Transactional(rollbackFor=Exception.class)
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

			Integer totalProdutosBons = 0;
			while (lerArquivo.hasNext()) {
				linhaArquivo = lerArquivo.nextLine();
				String[] produtoAux = linhaArquivo.split(",");

				Random random = new Random();

				List<Estabelecimento> listaEstabelecimentos = estabelecimentoRepository.findAll();

				Integer value = random.nextInt(listaEstabelecimentos.size());

				Long idRandomico = value.longValue();
				Cidade cidade = new Cidade();

				Integer count = 0;
				for (Estabelecimento est : listaEstabelecimentos) {
					count ++;
					if(est.getHashId().equals(produtoAux[2])) {
						idRandomico = count.longValue();
						totalProdutosBons ++;
					}
					if(est.getCidade().getNome().equals(produtoAux[6])) {
						cidade = est.getCidade();
					}
				}

				Produto produto = new Produto(produtoAux[0], idRandomico.equals(0l) ? 1l : idRandomico, produtoAux[4], Double.parseDouble(produtoAux[5]), cidade);

				produtoRepository.save(produto);

			}
			System.out.println("totalProdutos: " + totalProdutosBons);
			lerArquivo.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
