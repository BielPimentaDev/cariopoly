package com.cariopoly.backend;

import com.cariopoly.backend.core.application.usecase.iniciarUmJogo;
import com.cariopoly.backend.core.domain.entity.Jogo;
import com.cariopoly.backend.core.domain.entity.Jogador;
import com.cariopoly.backend.core.domain.entity.Tabuleiro;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class IniciarUmJogoTest {

    @Test
    public void iniciarUmJogoTest_withSuccess_twoPlayers() {
        Jogador p1 = new Jogador("Alice", 0, 1500);
        Jogador p2 = new Jogador("Bob", 0, 1500);
        List<Jogador> jogadores = Arrays.asList(p1, p2);
        Tabuleiro tabuleiro = new Tabuleiro(jogadores, Collections.emptyList());

        iniciarUmJogo useCase = new iniciarUmJogo(tabuleiro, jogadores);
        Jogo jogo = useCase.executar();

        assertNotNull(jogo, "O jogo não deve ser nulo após iniciar com 2 jogadores");
    }

    @Test
    public void iniciarUmJogoTest_withSuccess_fourPlayers() {
        Jogador p1 = new Jogador("P1", 0, 1500);
        Jogador p2 = new Jogador("P2", 0, 1500);
        Jogador p3 = new Jogador("P3", 0, 1500);
        Jogador p4 = new Jogador("P4", 0, 1500);
        List<Jogador> jogadores = Arrays.asList(p1, p2, p3, p4);
        Tabuleiro tabuleiro = new Tabuleiro(jogadores, Collections.emptyList());

        iniciarUmJogo useCase = new iniciarUmJogo(tabuleiro, jogadores);
        Jogo jogo = useCase.executar();

        assertNotNull(jogo, "O jogo não deve ser nulo após iniciar com 4 jogadores (limite)");
    }

    @Test
    public void iniciarUmJogoTest_withMoreThanFourPlayers_shouldThrow() {
        Jogador p1 = new Jogador("P1", 0, 1500);
        Jogador p2 = new Jogador("P2", 0, 1500);
        Jogador p3 = new Jogador("P3", 0, 1500);
        Jogador p4 = new Jogador("P4", 0, 1500);
        Jogador p5 = new Jogador("P5", 0, 1500);
        List<Jogador> jogadores = Arrays.asList(p1, p2, p3, p4, p5);
        Tabuleiro tabuleiro = new Tabuleiro(jogadores, Collections.emptyList());

        iniciarUmJogo useCase = new iniciarUmJogo(tabuleiro, jogadores);

        assertThrows(IllegalArgumentException.class, useCase::executar,
                "Deve lançar IllegalArgumentException quando mais de 4 jogadores são informados");
    }
}
