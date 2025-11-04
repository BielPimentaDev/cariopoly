package com.cariopoly.backend.core.application.usecase;

import com.cariopoly.backend.core.domain.entity.Jogador;
import com.cariopoly.backend.core.domain.entity.Jogo;
import com.cariopoly.backend.core.domain.entity.Tabuleiro;

import java.util.List;

public class iniciarUmJogo {
    private final Tabuleiro tabuleiro;
    private final List<Jogador> jogadores;

    public  iniciarUmJogo(Tabuleiro tabuleiro, List<Jogador> jogadores) {
        this.tabuleiro = tabuleiro;
        this.jogadores = jogadores;
    }

    public Jogo executar() {
        if (jogadores.size() > 4) {
            throw new IllegalArgumentException("O jogo suporta no máximo 4 jogadores.");
        }

        // exigir exatamente 10 casas no tabuleiro
        if (tabuleiro.getCasas().size() != 10) {
            throw new IllegalArgumentException("O jogo deve ter 10 casas.");

        }

        return new Jogo(tabuleiro, jogadores);
    }
}
