package br.ce.wcaquino.service;

import br.ce.wcaquino.exceptions.NaoPodeDividirPorZeroException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CalculadoraTest {

    private Calculadora calc;

    @Before
    public void setup() {
        calc = new Calculadora();
    }

    @Test
    public void deveSomarDoisValores() {
        //cenario
        int a = 5, b = 3;

        //acao
        int resultado = calc.somar(a, b);

        //verificacao
        Assert.assertEquals(8, resultado);
    }

    @Test
    public void deveSubtrairDoisValores() {
        //cenario
        int a = 5, b = 3;

        //acao
        int resultado = calc.subtrair(a, b);

        //verificacao
        Assert.assertEquals(2, resultado);
    }

    @Test
    public void deveMultiplicarDoisValores() {
        //cenario
        int a = 5, b = 3;

        //acao
        int resultado = calc.multiplicar(a, b);

        //verificacao
        Assert.assertEquals(15, resultado);
    }

    @Test
    public void deveDividirDoisValores() throws NaoPodeDividirPorZeroException{
        //cenario
        int a = 15, b = 3;

        //acao
        int resultado = calc.dividir(a, b);

        //verificacao
        Assert.assertEquals(5, resultado);
    }

    @Test(expected = NaoPodeDividirPorZeroException.class)
    public void deveLancarExcecaoAoDividirPorZero() throws NaoPodeDividirPorZeroException {
        //cenario
        int a = 10, b = 0;

        //acao
        int resultado = calc.dividir(a, b);

    }

    @Test
    public void deveDividir() {
        String a = "6";
        String b = "3";

        int resultado = calc.dividir(a,b);

        Assert.assertEquals(2, resultado);
    }
}
