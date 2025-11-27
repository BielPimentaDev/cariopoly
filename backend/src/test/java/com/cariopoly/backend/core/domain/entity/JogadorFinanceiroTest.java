package com.cariopoly.backend.core.domain.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class JogadorFinanceiroTest {

    @Test
    public void debitar_e_creditar_and_podePagar_behavior() {
        Jogador j = new Jogador("Teste", 0, 100);
        assertTrue(j.podePagar(50));
        assertFalse(j.podePagar(150));

        j.debitar(30);
        assertEquals(70, j.getBalance());

        j.creditar(50);
        assertEquals(120, j.getBalance());

        // allow balance to go negative via debitar
        j.debitar(200);
        assertEquals(-80, j.getBalance());
        assertFalse(j.podePagar(1));
    }
}

