package br.ce.wcaquino.builders;

import br.ce.wcaquino.entity.Filme;

public class FilmeBuilder {
    private Filme filme;

    private FilmeBuilder() {

    }

    public static  FilmeBuilder umFilme() {
        FilmeBuilder builder = new FilmeBuilder();
        builder.filme = new Filme();
        builder.filme.setNome("A volta dos que não foram");
        builder.filme.setEstoque(5);
        builder.filme.setPrecoLocacao(7.00);

        return builder;
    }

    public static  FilmeBuilder umFilmeSemEstoque() {
        FilmeBuilder builder = new FilmeBuilder();
        builder.filme = new Filme();
        builder.filme.setNome("A volta dos que não foram");
        builder.filme.setEstoque(0);
        builder.filme.setPrecoLocacao(7.00);

        return builder;
    }

    public FilmeBuilder comValor(Double valor) {
        filme.setPrecoLocacao(valor);

        return this;
    }

    public FilmeBuilder semEstoque() {
        filme.setEstoque(0);

        return this;
    }

    public Filme agora() {
        return filme;
    }
}
