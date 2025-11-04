package com.cariopoly.backend.core.domain.entity.casa;

import com.cariopoly.backend.core.domain.entity.Jogador;

import java.util.ArrayList;

public class Prisao extends Casa{

    public Prisao(String nome, int posicao) {
        super(nome, posicao);

    }

    @Override
    public void acao(Jogador jogador) {
        // Lógica para quando um jogador cai na prisão
        jogador.setEstaPreso();
    }

}
