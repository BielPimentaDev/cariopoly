package com.cariopoly.backend.infra.adapter;

import com.cariopoly.backend.core.application.port.JogoRepository;
import com.cariopoly.backend.core.domain.entity.Jogo;

import java.util.List;

public class InMemoryJogoRepository implements JogoRepository {
    private final List<Jogo> jogo;

    public InMemoryJogoRepository() {
        this.jogo = new java.util.ArrayList<>();
    }

    public void salvar(Jogo jogo) {
        this.jogo.add(jogo);
    }

    @Override
    public List<Jogo> listar() {
        return this.jogo;
    }
}
