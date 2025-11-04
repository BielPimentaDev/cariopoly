package com.cariopoly.backend.core.domain.entity;

import com.cariopoly.backend.core.domain.entity.casa.Propriedade;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PropriedadeTest {

    @Test
    public void constructor_and_getters_shouldReturnExpectedValues() {
        Propriedade p = new Propriedade("Av Paulista", 1, 200, 20);
        assertEquals("Av Paulista", p.getNome());
        assertEquals(1, p.getPosicao());
        assertEquals(200, p.getValorDeCompra());
        assertEquals(20, p.getValorDeAluguel());
        assertNull(p.getDono());
        assertFalse(p.isHipotecada());
    }
}

