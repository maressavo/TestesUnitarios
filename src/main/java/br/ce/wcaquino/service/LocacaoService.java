package br.ce.wcaquino.service;

import static br.ce.wcaquino.utils.DataUtils.adicionarDias;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import br.ce.wcaquino.entity.Filme;
import br.ce.wcaquino.entity.Locacao;
import br.ce.wcaquino.entity.Usuario;
import br.ce.wcaquino.exceptions.FilmeSemEstoqueException;
import br.ce.wcaquino.exceptions.LocadoraException;
import br.ce.wcaquino.utils.DataUtils;

/*
	Os códigos aqui apresentados são parte do curso Testes unitários em JAVA, replicados aqui com o objetivo de aperfeiçoamento
	do conhecimento sobre testes unitários.
 */
public class LocacaoService {
	public Locacao alugarFilme(Usuario usuario, List<Filme> filmes) throws FilmeSemEstoqueException, LocadoraException {
		if (usuario == null) {
			throw  new LocadoraException("Usuário vazio");
		}

		if (filmes == null || filmes.isEmpty()) {
			throw new LocadoraException("Filme vazio");
		}

		for (Filme filme: filmes) {
			if (filme.getEstoque() == 0) {
				throw new FilmeSemEstoqueException();
			}
		}

		Locacao locacao = new Locacao();
		locacao.setFilmes(filmes);
		locacao.setUsuario(usuario);
		locacao.setDataLocacao(new Date());
		Double valorTotal = 0d;
		for (int i = 0; i < filmes.size(); i++) {
			Filme filme = filmes.get(i);
			Double valorFilme = filme.getPrecoLocacao();
			if (i == 2) {
				valorFilme = valorFilme * 0.75;
			}
			if (i == 3) {
				valorFilme = valorFilme * 0.50;
			}
			if (i == 4) {
				valorFilme = valorFilme * 0.25;
			}
			if (i == 5) {
				valorFilme = valorFilme * 0;
			}
			valorTotal += valorFilme;
		}
		if (filmes.size()<3){
			locacao.setValor(valorTotal);
		}

		locacao.setValor(valorTotal);

		//Entrega no dia seguinte
		Date dataEntrega = new Date();
		dataEntrega = adicionarDias(dataEntrega, 1);
		if (DataUtils.verificarDiaSemana(dataEntrega, Calendar.SUNDAY)) {
			dataEntrega = adicionarDias(dataEntrega, 1);
		}
		locacao.setDataRetorno(dataEntrega);
		
		//Salvando a locacao...	
		//TODO adicionar método para salvar
		
		return locacao;
	}
}