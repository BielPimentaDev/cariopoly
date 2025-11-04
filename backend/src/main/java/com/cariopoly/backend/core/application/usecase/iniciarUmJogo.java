package com.cariopoly.backend.core.application.usecase;

import com.cariopoly.backend.core.application.port.JogoRepository;
import com.cariopoly.backend.core.domain.entity.Jogador;
import com.cariopoly.backend.core.domain.entity.Jogo;
import com.cariopoly.backend.core.domain.entity.Tabuleiro;

import java.util.List;

public class iniciarUmJogo {
    private final Tabuleiro tabuleiro;
    private final List<Jogador> jogadores;
    private final JogoRepository jogoRepository;

    public  iniciarUmJogo(Tabuleiro tabuleiro, List<Jogador> jogadores, JogoRepository jogoRepository) {
        this.tabuleiro = tabuleiro;
        this.jogadores = jogadores;
        this.jogoRepository = jogoRepository;
    }

    public Jogo executar() {
        if (jogadores.size() > 4) {
            throw new IllegalArgumentException("O jogo suporta no máximo 4 jogadores.");
        }

        // exigir exatamente 10 casas no tabuleiro
        if (tabuleiro.getCasas().size() != 10) {
            throw new IllegalArgumentException("O jogo deve ter 10 casas.");

        }

        Jogo jogo = new Jogo(tabuleiro, jogadores);
        // persistir o jogo
        if (this.jogoRepository != null) {
            this.jogoRepository.salvar(jogo);
        }
        return jogo;
    }
}
