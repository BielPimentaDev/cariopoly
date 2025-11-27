package com.cariopoly.backend;

import com.cariopoly.backend.core.application.port.JogoRepository;
import com.cariopoly.backend.core.domain.entity.Jogo;
import com.cariopoly.backend.core.domain.entity.casa.Casa;
import com.cariopoly.backend.infra.adapter.InMemoryJogoRepository;
import com.cariopoly.backend.core.domain.entity.Tabuleiro;
import com.cariopoly.backend.core.domain.entity.Jogador;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class InMemoryJogoRepositoryTest {

    @Test
    public void salvar_e_listar_deveFuncionar() {
        InMemoryJogoRepository repo = new InMemoryJogoRepository();

        List<Jogador> jogadores = new ArrayList<>();
        jogadores.add(new Jogador("A", 0, 100));
        List<Casa> casas = new ArrayList<>();
        Tabuleiro t = new Tabuleiro(jogadores, casas);

        Jogo jogo = new Jogo(t, jogadores);
        repo.salvar(jogo);

        List<Jogo> todos = repo.listar();
        assertNotNull(todos);
        assertEquals(1, todos.size());
        assertSame(jogo, todos.get(0));
    }
}
