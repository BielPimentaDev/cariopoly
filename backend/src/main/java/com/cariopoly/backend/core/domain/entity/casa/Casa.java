package com.cariopoly.backend.core.domain.entity.casa;

import com.cariopoly.backend.core.domain.entity.Jogador;

public abstract class Casa {

    protected final String nome;
    protected final int posicao;

    public Casa(String nome, int posicao) {
        this.nome = nome;
        this.posicao = posicao;
    }

    public abstract void acao(Jogador jogador);


    public String getNome() { return nome; }
    public int getPosicao() { return posicao; }
}
