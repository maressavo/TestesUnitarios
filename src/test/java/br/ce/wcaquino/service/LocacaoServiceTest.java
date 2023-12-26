package br.ce.wcaquino.service;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.exceptions.FilmeSemEstoqueException;
import br.ce.wcaquino.exceptions.LocadoraException;
import br.ce.wcaquino.servicos.LocacaoService;
import static br.ce.wcaquino.utils.DataUtils.isMesmaData;
import static br.ce.wcaquino.utils.DataUtils.obterDataComDiferencaDias;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class LocacaoServiceTest {

    private LocacaoService service;
    private Locacao resultado;

    @Rule
    public ErrorCollector error = new ErrorCollector();

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setup(){
        service = new LocacaoService();
    }
    @Test
    public void deveAlugarFilmeComSucesso() throws Exception {
        //cenario
        Usuario usuario = new Usuario("Joana");
        //Filme filme = new Filme("A volta dos que não foram", 2, 7.85);
        List<Filme> filmes = Arrays.asList(new Filme("A volta dos que não foram", 2, 7.85));

        //acao
        Locacao locacao = service.alugarFilme(usuario,filmes);

        //verificacao
        /*Assert.assertTrue(locacao.getValor() == 7.85);
        Assert.assertTrue(DataUtils.isMesmaData(locacao.getDataLocacao(), new Date()));
        Assert.assertTrue(DataUtils.isMesmaData(locacao.getDataRetorno(), DataUtils.obterDataComDiferencaDias(1)));*/
        error.checkThat(locacao.getValor(), is(equalTo(7.85)));
        error.checkThat(isMesmaData(locacao.getDataLocacao(), new Date()), is(true));
        error.checkThat(isMesmaData(locacao.getDataRetorno(), obterDataComDiferencaDias(1)), is(true));
    }

    @Test(expected = FilmeSemEstoqueException.class)
    public void naoDeveAlugarFilmeSemEstoque() throws Exception{
        //cenario
        Usuario usuario = new Usuario("Joana");
        //Filme filme = new Filme("A volta dos que não foram", 2, 7.85);
        List<Filme> filmes = Arrays.asList(new Filme("A volta dos que não foram", 0, 7.85));

        //acao
        service.alugarFilme(usuario, filmes);
    }

    @Test
    public void naoDeveAlugarFilmeSemUsuario () throws FilmeSemEstoqueException {
        //cenario
        List<Filme> filmes = Arrays.asList(new Filme("A volta dos que não foram", 0, 7.85));
        //acao
        try {
            service.alugarFilme(null, filmes);
            Assert.fail();
        } catch (LocadoraException e) {
            assertThat(e.getMessage(), is("Usuário vazio"));
        }
    }

    @Test
    public void naoDeveAlugarFilmeSemFilme() throws FilmeSemEstoqueException, LocadoraException{
        //cenario
        Usuario usuario = new Usuario("Usuario 1");

        exception.expect(LocadoraException.class);
        exception.expectMessage("Filme vazio");

        //acao
        service.alugarFilme(usuario, null);
    }

    @Test
    public void deveDescontar25PctNo3Filme() throws FilmeSemEstoqueException, LocadoraException {
        Usuario usuario = new Usuario("Joana");

        List<Filme> filmes = Arrays.asList(
                new Filme("A volta dos que não foram", 5, 18.00),
                new Filme("As tranças da vovó careca", 2, 10.00),
                new Filme("Esqueceram de mim 7", 7, 20.00)
        );

        resultado = service.alugarFilme(usuario, filmes);

        assertThat(resultado.getValor(), is(43.00));
    }
    @Test
    public void deveDescontar50PctNo4Filme() throws FilmeSemEstoqueException, LocadoraException {
        Usuario usuario = new Usuario("Joana");

        List<Filme> filmes = Arrays.asList(
                new Filme("A volta dos que não foram", 5, 18.00),
                new Filme("As tranças da vovó careca", 2, 10.00),
                new Filme("Esqueceram de mim 7", 7, 20.00),
                new Filme("Operação Feliz Natal", 8, 25.00)
        );

        resultado = service.alugarFilme(usuario, filmes);

        assertThat(resultado.getValor(), is(55.50));
    }

    @Test
    public void deveDescontar75PctNo5Filme() throws FilmeSemEstoqueException, LocadoraException {
        Usuario usuario = new Usuario("Joana");

        List<Filme> filmes = Arrays.asList(
                new Filme("A volta dos que não foram", 5, 18.00),
                new Filme("As tranças da vovó careca", 2, 10.00),
                new Filme("Esqueceram de mim 7", 7, 20.00),
                new Filme("Operação Feliz Natal", 8, 25.00),
                new Filme("Uma História de Natal Natalina", 2, 15.00)
        );
        resultado = service.alugarFilme(usuario, filmes);

        assertThat(resultado.getValor(), is(59.25));
    }
    @Test
    public void deveDescontar100PctNo6Filme() throws FilmeSemEstoqueException, LocadoraException {
        Usuario usuario = new Usuario("Joana");

        List<Filme> filmes = Arrays.asList(
                new Filme("A volta dos que não foram", 5, 18.00),
                new Filme("As tranças da vovó careca", 2, 10.00),
                new Filme("Esqueceram de mim 7", 7, 20.00),
                new Filme("Operação Feliz Natal", 8, 25.00),
                new Filme("Uma História de Natal Natalina", 3, 15.00),
                new Filme("12 Vésperas de Natal", 10, 12.00)
        );
        resultado = service.alugarFilme(usuario, filmes);

        assertThat(resultado.getValor(), is(59.25));
    }
}
