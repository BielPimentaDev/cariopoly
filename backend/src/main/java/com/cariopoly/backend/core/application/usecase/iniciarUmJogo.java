package com.cariopoly.backend.core.application.usecase;

import com.cariopoly.backend.core.domain.entity.Jogador;
import com.cariopoly.backend.core.domain.entity.Jogo;
import com.cariopoly.backend.core.domain.entity.Tabuleiro;

import java.util.List;

public class iniciarUmJogo {
    private Tabuleiro tabuleiro;
    private  List<Jogador> jogadores;

    public  iniciarUmJogo(Tabuleiro tabuleiro, List<Jogador> jogadores) {
        this.tabuleiro = tabuleiro;
        this.jogadores = jogadores;
    }

    public Jogo executar() {
        if (jogadores.size() > 4) {
            throw new IllegalArgumentException("O jogo suporta no máximo 4 jogadores.");
        }

        return new Jogo(tabuleiro, jogadores);
    };
}
