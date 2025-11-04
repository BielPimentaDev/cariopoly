package com.cariopoly.backend.core.domain.entity;

import com.cariopoly.backend.core.domain.entity.casa.Casa;

import java.util.List;

public class Tabuleiro {
    public List<Jogador> jogadores;
    public List<Casa> casas;

    public Tabuleiro(List<Jogador> jogadores, List<Casa> casas) {
        this.jogadores = jogadores;
        this.casas = casas;
    }
}
