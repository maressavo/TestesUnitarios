package br.ce.wcaquino.entidades;

import lombok.Data;

import java.util.Date;
@Data
public class Locacao {

	private Usuario usuario;
	private Filme filme;
	private Date dataLocacao;
	private Date dataRetorno;
	private Double valor;
}