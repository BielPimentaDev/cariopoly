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
        // comportamento padrão: se não houver dono, tenta comprar automaticamente
        if (this.dono == null) {
            this.comprar(jogador);
        } else if (!this.dono.getId().equals(jogador.getId())) {
            // se estiver hipotecada, não cobra aluguel
            this.cobrarAluguel(jogador);
        }

    }

    /**
     * Tenta comprar a propriedade para o jogador informado.
     * Retorna true se a compra foi efetuada, false caso o jogador não tenha saldo suficiente
     */
    public boolean comprar(Jogador comprador) {
        if (comprador == null) return false;
        if (this.dono != null) return false; // já tem dono
        if (!comprador.podePagar(this.valorDeCompra)) {
            return false;
        }
        comprador.debitar(this.valorDeCompra);
        this.dono = comprador;
        comprador.adicionarPropriedade(this);
        return true;
    }

    /**
     * Cobra aluguel do visitante; se a propriedade estiver hipotecada ou não tiver dono, não faz nada.
     * Este método debita o visitante e credita o dono. Permite saldo negativo para o visitante (simula falência externa).
     */
    public void cobrarAluguel(Jogador visitante) {
        if (visitante == null) return;
        if (this.dono == null) return;
        if (this.hipotecada) return;
        if (this.dono.getId().equals(visitante.getId())) return; // dono não paga a si mesmo

        visitante.debitar(this.valorDeAluguel);
        this.dono.creditar(this.valorDeAluguel);
    }

    public void hipotecar() {
        this.hipotecada = true;
    }

    public void desfazerHipoteca() {
        this.hipotecada = false;
    }

}
