package com.cariopoly.backend;

import com.cariopoly.backend.core.domain.entity.Jogo;
import com.cariopoly.backend.core.domain.entity.Tabuleiro;
import com.cariopoly.backend.core.domain.entity.Jogador;
import com.cariopoly.backend.core.domain.entity.casa.Casa;
import com.cariopoly.backend.infra.adapter.InMemoryJogoRepository;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class InMemoryJogoRepositoryAdditionalTest {

    private Jogo createJogo(String jogadorName) {
        List<Jogador> jogadores = new ArrayList<>();
        jogadores.add(new Jogador(jogadorName, 0, 100));
        List<Casa> casas = new ArrayList<>();
        Tabuleiro t = new Tabuleiro(jogadores, casas);
        return new Jogo(t, jogadores);
    }

    @Test
    public void salvar_multiple_preserves_order() {
        InMemoryJogoRepository repo = new InMemoryJogoRepository();

        Jogo first = createJogo("A");
        Jogo second = createJogo("B");

        repo.salvar(first);
        repo.salvar(second);

        List<Jogo> todos = repo.listar();
        assertNotNull(todos);
        assertEquals(2, todos.size());
        assertSame(first, todos.get(0));
        assertSame(second, todos.get(1));
    }

    @Test
    public void listar_returns_live_view_mutations_reflect_in_repository() {
        InMemoryJogoRepository repo = new InMemoryJogoRepository();

        Jogo jogo = createJogo("Live");
        repo.salvar(jogo);

        List<Jogo> returned = repo.listar();
        // clearing the returned list should clear the repository storage since it's the same instance
        returned.clear();

        assertTrue(repo.listar().isEmpty(), "Repository should reflect cleared returned list");
    }

    @Test
    public void salvar_allows_duplicate_entries() {
        InMemoryJogoRepository repo = new InMemoryJogoRepository();

        Jogo jogo = createJogo("Dup");
        repo.salvar(jogo);
        repo.salvar(jogo); // save same instance twice

        List<Jogo> todos = repo.listar();
        assertEquals(2, todos.size());
        assertSame(jogo, todos.get(0));
        assertSame(jogo, todos.get(1));
    }
}
