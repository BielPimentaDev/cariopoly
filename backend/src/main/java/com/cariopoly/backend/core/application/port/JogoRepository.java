package com.cariopoly.backend.core.application.port;

import com.cariopoly.backend.core.domain.entity.Jogo;

import java.util.List;

public interface JogoRepository {

    void salvar(Jogo jogo);

    // retorna todos os jogos salvos (útil para testes e operações simples)
    List<Jogo> listar();
}
