package com.cariopoly.backend.core.domain.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class JogadorTest {

    @Test
    public void mover_shouldWrapAroundBoard() {
        Jogador j = new Jogador("Alice", 38, 1500);
        j.mover(5); // 38 + 5 = 43 % 40 = 3
        assertEquals(3, j.getPosition());
    }

    @Test
    public void setEstaPreso_shouldSetFlagsAndTurns() {
        Jogador j = new Jogador("Bob", 0, 1500);
        assertFalse(j.isEstaPreso());
        j.setEstaPreso();
        assertTrue(j.isEstaPreso());
        assertEquals(3, j.getTurnosRestantesNaPrisão());
    }
}

