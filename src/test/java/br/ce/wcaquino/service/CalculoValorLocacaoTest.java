package br.ce.wcaquino.service;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.exceptions.FilmeSemEstoqueException;
import br.ce.wcaquino.exceptions.LocadoraException;
import br.ce.wcaquino.servicos.LocacaoService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(Parameterized.class)
public class CalculoValorLocacaoTest {
    private LocacaoService service;

    @Parameterized.Parameter
    public List<Filme> filmes;
    @Parameterized.Parameter(value=1)
    public Double valorLocacao;

    @Parameterized.Parameter(value = 2)
    public String cenario;

    @Before
    public void setup() {
        service = new LocacaoService();
    }

    private static Filme filme1 = new Filme("A volta dos que não foram", 5, 18.00);
    private static Filme filme2 = new Filme("As tranças da vovó careca", 2, 10.00);
    private static Filme filme3 = new Filme("Esqueceram de mim 7", 7, 20.00);
    private static Filme filme4 = new Filme("Operação Feliz Natal", 8, 25.00);
    private static Filme filme5 = new Filme("Uma História de Natal Natalina", 3, 15.00);
    private static Filme filme6 = new Filme("12 Vésperas de Natal", 10, 12.00);
    private static Filme filme7 = new Filme("Entrando no clima do Natal", 10, 14.00);

    @Parameterized.Parameters(name = "{2}")
    public static Collection<Object[]> getParametros() {
        return Arrays.asList(new Object[][] {
                {Arrays.asList(filme1, filme2), 28.00, "2 Filmes: Sem Descontos"},
                {Arrays.asList(filme1, filme2, filme3), 43.00, "3 Filmes: 25%"},
                {Arrays.asList(filme1, filme2, filme3, filme4), 55.50, "4 Filmes: 50%"},
                {Arrays.asList(filme1, filme2, filme3, filme4, filme5), 59.25, "5 Filmes: 75%"},
                {Arrays.asList(filme1, filme2, filme3, filme4, filme5, filme6), 59.25, "6 Filmes: 100%"},
                {Arrays.asList(filme1, filme2, filme3, filme4, filme5, filme6, filme7), 73.25, "7 Filmes: Sem Descontos"}
        });
    }

    @Test
    public void deveCalcularValorLocacaoConsiderandoDescontos() throws FilmeSemEstoqueException, LocadoraException {
        Usuario usuario = new Usuario("Gabriela Cravo e Canela");

        Locacao resultado = service.alugarFilme(usuario, filmes);

        assertThat(resultado.getValor(), is(valorLocacao));
    }

    @Test
    public void print() {
        System.out.println(valorLocacao);
    }

}
