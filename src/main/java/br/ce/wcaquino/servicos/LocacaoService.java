package br.ce.wcaquino.servicos;

import static br.ce.wcaquino.utils.DataUtils.adicionarDias;

import java.util.Date;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
/*
	Os códigos aqui apresentados são parte do curso Testes unitários em JAVA, replicados aqui com o objetivo de aperfeiçoamento
	do conhecimento sobre testes unitários.
 */
public class LocacaoService {
	
	public Locacao alugarFilme(Usuario usuario, Filme filme) {
		Locacao locacao = new Locacao();
		locacao.setFilme(filme);
		locacao.setUsuario(usuario);
		locacao.setDataLocacao(new Date());
		locacao.setValor(filme.getPrecoLocacao());

		//Entrega no dia seguinte
		Date dataEntrega = new Date();
		dataEntrega = adicionarDias(dataEntrega, 1);
		locacao.setDataRetorno(dataEntrega);
		
		//Salvando a locacao...	
		//TODO adicionar método para salvar
		
		return locacao;
	}

	public static void main(String[] args) {
		//cenario
		LocacaoService service = new LocacaoService();
		Usuario usuario = new Usuario("Joana");
		Filme filme = new Filme("A volta dos que não foram", 2, 7.85);

		//acao
		Locacao locacao = service.alugarFilme(usuario,filme);

		//verificacao

		System.out.println(locacao.getValor());
		System.out.println(locacao.getDataLocacao());
		System.out.println(locacao.getDataRetorno());
	}
}