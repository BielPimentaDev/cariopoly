package com.cariopoly.backend.core.domain.entity;

import java.util.List;

public class Jogo {

    private Tabuleiro tabuleiro;
    private List<Jogador> jogador;

    public Jogo(Tabuleiro tabuleiro, List<Jogador> jogador) {
        this.tabuleiro = tabuleiro;
        this.jogador = jogador;
    }


}
