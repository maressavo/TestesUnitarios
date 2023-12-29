package br.ce.wcaquino.service;

import br.ce.wcaquino.daos.LocacaoDAO;
import br.ce.wcaquino.daos.LocacaoDAOFalse;
import br.ce.wcaquino.entity.Filme;
import br.ce.wcaquino.entity.Locacao;
import br.ce.wcaquino.entity.Usuario;
import br.ce.wcaquino.exceptions.FilmeSemEstoqueException;
import br.ce.wcaquino.exceptions.LocadoraException;
import br.ce.wcaquino.utils.DataUtils;
import buildermaster.BuilderMaster;
import org.junit.*;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static br.ce.wcaquino.builders.FilmeBuilder.umFilme;
import static br.ce.wcaquino.builders.FilmeBuilder.umFilmeSemEstoque;
import static br.ce.wcaquino.builders.UsuarioBuilder.umUsuario;
import static br.ce.wcaquino.matchers.MatchersProprios.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class LocacaoServiceTest {

    private LocacaoService service;

    @Rule
    public ErrorCollector error = new ErrorCollector();

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setup(){
        service = new LocacaoService();
        LocacaoDAO dao = new LocacaoDAOFalse();
        service.setLocacaoDAO(dao);
    }
    @Test
    public void deveAlugarFilmeComSucesso() throws Exception {
        Assume.assumeFalse(DataUtils.verificarDiaSemana(new Date(), Calendar.SATURDAY));

        Usuario usuario = umUsuario().agora();

        List<Filme> filmes = Arrays.asList(umFilme().comValor(7.85).agora());

        Locacao locacao = service.alugarFilme(usuario,filmes);

        error.checkThat(locacao.getValor(), is(equalTo(7.85)));
        error.checkThat(locacao.getDataLocacao(), ehHoje());
        error.checkThat(locacao.getDataRetorno(), ehHojeComDiferencaDias(1));
    }

    @Test(expected = FilmeSemEstoqueException.class)
    public void naoDeveAlugarFilmeSemEstoque() throws Exception{
        Usuario usuario = umUsuario().agora();

        List<Filme> filmes = Arrays.asList(umFilmeSemEstoque().agora());

        service.alugarFilme(usuario, filmes);
    }

    @Test
    public void naoDeveAlugarFilmeSemUsuario () throws FilmeSemEstoqueException {
        List<Filme> filmes = Arrays.asList(umFilme().agora());

        try {
            service.alugarFilme(null, filmes);
            Assert.fail();
        } catch (LocadoraException e) {
            assertThat(e.getMessage(), is("Usuário vazio"));
        }
    }

    @Test
    public void naoDeveAlugarFilmeSemFilme() throws FilmeSemEstoqueException, LocadoraException{
        Usuario usuario = umUsuario().agora();

        exception.expect(LocadoraException.class);
        exception.expectMessage("Filme vazio");

        service.alugarFilme(usuario, null);
    }

    @Test
    public void naoDeveDevolverFilmesAosDomingos() throws FilmeSemEstoqueException, LocadoraException {//Se o filme for alugado aos sábados a devolução é na segunda-feira.
        Assume.assumeTrue(DataUtils.verificarDiaSemana(new Date(), Calendar.SATURDAY));

        Usuario usuario = umUsuario().agora();

        List<Filme> filmes = Arrays.asList(umFilme().agora());

        Locacao retorno = service.alugarFilme(usuario, filmes);

        assertThat(retorno.getDataRetorno(), caiEmUmaSegunda());
    }

    public static void main(String[] args) {
        new BuilderMaster().gerarCodigoClasse(Locacao.class);
    }
}