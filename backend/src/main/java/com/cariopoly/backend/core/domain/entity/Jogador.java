package com.cariopoly.backend.core.domain.entity;


import com.cariopoly.backend.core.domain.entity.casa.Propriedade;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Jogador {
    private String id;
    private String name;
    private int position;
    private int balance;
    private List<Propriedade> propriedades;
    private boolean estaPreso;
    private int turnosRestantesNaPrisão;

    public Jogador(String name, int position, int balance) {
        this.id = java.util.UUID.randomUUID().toString();
        this.name = name;
        this.position = position;
        this.balance = balance;
        this.propriedades = new ArrayList<>();
    }

    public Jogador(String id, String name, int position, int balance) {
        this.id = id;
        this.name = name;
        this.position = position;
        this.balance = balance;
        this.propriedades = new ArrayList<>();
    }

    public void mover(int passos) {
        this.position = (this.position + passos) % 40;
    }

    public void setEstaPreso(){
        this.estaPreso = true;
        this.turnosRestantesNaPrisão = 3;
    }

    // Novos métodos para gerenciamento financeiro e de propriedades
    public boolean podePagar(int amount) {
        return this.balance >= amount;
    }

    public void debitar(int amount) {
        this.balance -= amount;
    }

    public void creditar(int amount) {
        this.balance += amount;
    }

    public void adicionarPropriedade(Propriedade p) {
        if (p != null && !this.propriedades.contains(p)) {
            this.propriedades.add(p);
        }
    }

    public void removerPropriedade(Propriedade p) {
        if (p != null) {
            this.propriedades.remove(p);
        }
    }
}
