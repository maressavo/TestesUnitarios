package br.ce.wcaquino.service;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.exceptions.FilmeSemEstoqueException;
import br.ce.wcaquino.exceptions.LocadoraException;
import br.ce.wcaquino.matchers.DiaSemanaMatcher;
import br.ce.wcaquino.matchers.MatchersProprios;
import br.ce.wcaquino.servicos.LocacaoService;

import static br.ce.wcaquino.matchers.MatchersProprios.*;
import static br.ce.wcaquino.utils.DataUtils.isMesmaData;
import static br.ce.wcaquino.utils.DataUtils.obterDataComDiferencaDias;

import br.ce.wcaquino.utils.DataUtils;
import org.junit.*;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;

import java.util.*;

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
        Assume.assumeFalse(DataUtils.verificarDiaSemana(new Date(), Calendar.SATURDAY));

        Usuario usuario = new Usuario("Joana");

        List<Filme> filmes = Arrays.asList(
                new Filme("A volta dos que não foram", 2, 7.85)
        );

        Locacao locacao = service.alugarFilme(usuario,filmes);

        error.checkThat(locacao.getValor(), is(equalTo(7.85)));
        error.checkThat(locacao.getDataLocacao(), ehHoje());
        error.checkThat(locacao.getDataRetorno(), ehHojeComDiferencaDias(1));
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
    public void naoDeveDevolverFilmesAosDomingos() throws FilmeSemEstoqueException, LocadoraException {//Se o filme for alugado aos sábados a devolução é na segunda-feira.
        Assume.assumeTrue(DataUtils.verificarDiaSemana(new Date(), Calendar.SATURDAY));

        Usuario usuario = new Usuario("Mariana Ruibarbo");

        List<Filme> filmes = Arrays.asList(
                new Filme("A volta dos que não foram", 5, 18.00)
        );

        Locacao retorno = service.alugarFilme(usuario, filmes);

        boolean eSegunda = DataUtils.verificarDiaSemana(retorno.getDataRetorno(), Calendar.MONDAY);
        Assert.assertTrue(eSegunda);

        assertThat(retorno.getDataRetorno(), caiEmUmaSegunda());
    }
}