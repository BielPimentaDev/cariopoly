package com.cariopoly.backend.core.domain.entity;

import com.cariopoly.backend.core.domain.entity.casa.Propriedade;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PropriedadeCompraAluguelTest {

    @Test
    public void comprar_deveFuncionarQuandoJogadorTemSaldo() {
        Jogador comprador = new Jogador("Comprador", 0, 300);
        Propriedade p = new Propriedade("Av Teste", 1, 200, 20);

        boolean resultado = p.comprar(comprador);

        assertTrue(resultado);
        assertEquals(100, comprador.getBalance());
        assertSame(comprador, p.getDono());
        assertTrue(comprador.getPropriedades().contains(p));
    }

    @Test
    public void comprar_deveFalharQuandoSaldoInsuficiente() {
        Jogador comprador = new Jogador("Comprador", 0, 100);
        Propriedade p = new Propriedade("Av Teste", 1, 200, 20);

        boolean resultado = p.comprar(comprador);

        assertFalse(resultado);
        assertNull(p.getDono());
        assertEquals(100, comprador.getBalance());
    }

    @Test
    public void cobrarAluguel_deveTransferirValorDoVisitanteParaDono() {
        Jogador dono = new Jogador("Dono", 0, 500);
        Jogador visitante = new Jogador("Visitante", 0, 300);
        Propriedade p = new Propriedade("Av Teste", 1, 200, 50);

        // simula compra
        p.comprar(dono);

        p.cobrarAluguel(visitante);

        assertEquals(250, visitante.getBalance());
        // owner paid 200 to buy, so 500 - 200 + 50 = 350
        assertEquals(350, dono.getBalance());
    }

    @Test
    public void cobrarAluguel_naoDeveCobrarQuandoHipotecadaOuSemDono() {
        Jogador dono = new Jogador("Dono", 0, 500);
        Jogador visitante = new Jogador("Visitante", 0, 300);
        Propriedade p = new Propriedade("Av Teste", 1, 200, 50);

        // sem dono: não altera saldos
        p.cobrarAluguel(visitante);
        assertEquals(300, visitante.getBalance());

        // quando hipotecada: não cobra
        p.comprar(dono);
        p.hipotecar();
        p.cobrarAluguel(visitante);
        assertEquals(300, visitante.getBalance());
        assertEquals(300, dono.getBalance());
    }
}