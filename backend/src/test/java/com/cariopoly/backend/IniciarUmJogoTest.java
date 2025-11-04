package com.cariopoly.backend;

import com.cariopoly.backend.core.application.usecase.iniciarUmJogo;
import com.cariopoly.backend.core.domain.entity.Jogo;
import com.cariopoly.backend.core.domain.entity.Jogador;
import com.cariopoly.backend.core.domain.entity.Tabuleiro;
import com.cariopoly.backend.core.domain.entity.casa.Casa;
import com.cariopoly.backend.core.domain.entity.casa.Propriedade;
import com.cariopoly.backend.core.domain.entity.casa.Prisao;
import com.cariopoly.backend.infra.adapter.InMemoryJogoRepository;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class IniciarUmJogoTest {

    // helper para criar uma lista de casas com tamanho variável
    private List<Casa> criarCasas(int quantidade) {
        // criar casas simples reusáveis: propriedades e prisões alternadas
        Casa[] casas = new Casa[quantidade];
        for (int i = 0; i < quantidade; i++) {
            if (i % 5 == 0) {
                casas[i] = new Prisao("Prisao" + i, i);
            } else {
                casas[i] = new Propriedade("Prop" + i, i, 100 + i, 10 + i);
            }
        }
        return Arrays.asList(casas);
    }

    @Test
    public void iniciarUmJogoTest_withSuccess_twoPlayers_exactly10Casas() {
        Jogador p1 = new Jogador("Alice", 0, 1500);
        Jogador p2 = new Jogador("Bob", 0, 1500);
        List<Jogador> jogadores = Arrays.asList(p1, p2);

        List<Casa> casas = criarCasas(10);
        Tabuleiro tabuleiro = new Tabuleiro(jogadores, casas);

        InMemoryJogoRepository repo = new InMemoryJogoRepository();
        iniciarUmJogo useCase = new iniciarUmJogo(tabuleiro, jogadores, repo);
        Jogo jogo = useCase.executar();

        assertNotNull(jogo, "O jogo não deve ser nulo após iniciar com 2 jogadores e 10 casas");
    }

    @Test
    public void iniciarUmJogoTest_withSuccess_fourPlayers_exactly10Casas() {
        Jogador p1 = new Jogador("P1", 0, 1500);
        Jogador p2 = new Jogador("P2", 0, 1500);
        Jogador p3 = new Jogador("P3", 0, 1500);
        Jogador p4 = new Jogador("P4", 0, 1500);
        List<Jogador> jogadores = Arrays.asList(p1, p2, p3, p4);

        List<Casa> casas = criarCasas(10);
        Tabuleiro tabuleiro = new Tabuleiro(jogadores, casas);

        InMemoryJogoRepository repo = new InMemoryJogoRepository();
        iniciarUmJogo useCase = new iniciarUmJogo(tabuleiro, jogadores, repo);
        Jogo jogo = useCase.executar();

        assertNotNull(jogo, "O jogo não deve ser nulo após iniciar com 4 jogadores e 10 casas (limite)");
    }

    @Test
    public void iniciarUmJogoTest_withMoreThanFourPlayers_shouldThrow() {
        Jogador p1 = new Jogador("P1", 0, 1500);
        Jogador p2 = new Jogador("P2", 0, 1500);
        Jogador p3 = new Jogador("P3", 0, 1500);
        Jogador p4 = new Jogador("P4", 0, 1500);
        Jogador p5 = new Jogador("P5", 0, 1500);
        List<Jogador> jogadores = Arrays.asList(p1, p2, p3, p4, p5);

        List<Casa> casas = criarCasas(10);
        Tabuleiro tabuleiro = new Tabuleiro(jogadores, casas);

        InMemoryJogoRepository repo = new InMemoryJogoRepository();
        iniciarUmJogo useCase = new iniciarUmJogo(tabuleiro, jogadores, repo);

        assertThrows(IllegalArgumentException.class, useCase::executar,
                "Deve lançar IllegalArgumentException quando mais de 4 jogadores são informados");
    }

    @Test
    public void iniciarUmJogoTest_tabuleiroWithNineCasas_shouldThrow() {
        Jogador p1 = new Jogador("Alice", 0, 1500);
        Jogador p2 = new Jogador("Bob", 0, 1500);
        List<Jogador> jogadores = Arrays.asList(p1, p2);

        List<Casa> casas = criarCasas(9);
        Tabuleiro tabuleiro = new Tabuleiro(jogadores, casas);

        InMemoryJogoRepository repo = new InMemoryJogoRepository();
        iniciarUmJogo useCase = new iniciarUmJogo(tabuleiro, jogadores, repo);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, useCase::executar);
        assertEquals("O jogo deve ter 10 casas.", ex.getMessage());
    }

    @Test
    public void iniciarUmJogoTest_tabuleiroWithElevenCasas_shouldThrow() {
        Jogador p1 = new Jogador("Alice", 0, 1500);
        Jogador p2 = new Jogador("Bob", 0, 1500);
        List<Jogador> jogadores = Arrays.asList(p1, p2);

        List<Casa> casas = criarCasas(11);
        Tabuleiro tabuleiro = new Tabuleiro(jogadores, casas);

        InMemoryJogoRepository repo = new InMemoryJogoRepository();
        iniciarUmJogo useCase = new iniciarUmJogo(tabuleiro, jogadores, repo);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, useCase::executar);
        assertEquals("O jogo deve ter 10 casas.", ex.getMessage());
    }

    @Test
    public void iniciarUmJogoTest_shouldPersistGame() {
        Jogador p1 = new Jogador("Alice", 0, 1500);
        Jogador p2 = new Jogador("Bob", 0, 1500);
        List<Jogador> jogadores = Arrays.asList(p1, p2);

        List<Casa> casas = criarCasas(10);
        Tabuleiro tabuleiro = new Tabuleiro(jogadores, casas);

        InMemoryJogoRepository repo = new InMemoryJogoRepository();
        iniciarUmJogo useCase = new iniciarUmJogo(tabuleiro, jogadores, repo);
        Jogo jogo = useCase.executar();

        // verifica que o jogo foi salvo no repositório em memória
        List<Jogo> jogosSalvos = repo.listar();
        assertEquals(1, jogosSalvos.size());
        assertSame(jogosSalvos.get(0), jogo);
    }
}
