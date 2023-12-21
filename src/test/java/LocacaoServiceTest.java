import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.servicos.LocacaoService;
import br.ce.wcaquino.utils.DataUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;

public class LocacaoServiceTest {
    //public static void main(String[] args) {
    @Test
    public void test(){
        //cenario
        LocacaoService service = new LocacaoService();
        Usuario usuario = new Usuario("Joana");
        Filme filme = new Filme("A volta dos que não foram", 2, 7.85);

        //acao
        Locacao locacao = service.alugarFilme(usuario,filme);

        //verificacao
        Assert.assertTrue(locacao.getValor() == 7.85);
        Assert.assertTrue(DataUtils.isMesmaData(locacao.getDataLocacao(), new Date()));
        Assert.assertTrue(DataUtils.isMesmaData(locacao.getDataRetorno(), DataUtils.obterDataComDiferencaDias(1)));
    }
}
