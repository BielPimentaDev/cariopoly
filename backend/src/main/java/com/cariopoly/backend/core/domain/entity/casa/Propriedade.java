package com.cariopoly.backend.core.domain.entity.casa;


import com.cariopoly.backend.core.domain.entity.Jogador;
import lombok.Getter;

@Getter
public class Propriedade extends Casa {

    protected int valorDeCompra;
    protected Jogador dono;
    protected boolean hipotecada;
    protected int valorDeAluguel;

    public Propriedade(String nome, int posicao, int valorDeCompra, int valorDeAluguel) {
        super(nome, posicao);
        this.valorDeCompra = valorDeCompra;
        this.dono = null;
        this.hipotecada = false;
        this.valorDeAluguel = valorDeAluguel;
    }

    @Override
    public void acao(Jogador jogador) {

        }

}
