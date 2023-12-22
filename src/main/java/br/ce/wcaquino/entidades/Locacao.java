package br.ce.wcaquino.entidades;

import lombok.Data;

import java.util.Date;
import java.util.List;

/*
	Os códigos aqui apresentados são parte do curso Testes unitários em JAVA, replicados aqui com o objetivo de aperfeiçoamento
	do conhecimento sobre testes unitários.
 */
@Data
public class Locacao {

	private Usuario usuario;
	private List<Filme> filmes;
	private Date dataLocacao;
	private Date dataRetorno;
	private Double valor;
}