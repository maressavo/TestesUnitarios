package br.ce.wcaquino.suites;

import br.ce.wcaquino.service.CalculadoraTest;
import br.ce.wcaquino.service.CalculoValorLocacaoTest;
import br.ce.wcaquino.service.LocacaoServiceTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        CalculadoraTest.class,
        CalculoValorLocacaoTest.class,
        LocacaoServiceTest.class
})
public class SuiteExecucao {
}
