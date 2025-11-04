package com.cariopoly.backend.core.domain.entity;

import com.cariopoly.backend.core.domain.entity.casa.Prisao;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PrisaoTest {

    @Test
    public void acao_shouldSetJogadorPreso() {
        Prisao prisao = new Prisao("Prisao", 5);
        Jogador j = new Jogador("Bob", 0, 1500);
        assertFalse(j.isEstaPreso());
        prisao.acao(j);
        assertTrue(j.isEstaPreso());
        assertEquals(3, j.getTurnosRestantesNaPrisão());
    }
}

