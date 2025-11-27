package com.cariopoly.backend.core.domain.entity;

import com.cariopoly.backend.core.domain.entity.casa.Casa;
import com.cariopoly.backend.core.domain.entity.casa.Propriedade;
import com.cariopoly.backend.core.domain.entity.casa.Prisao;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Classe que inicia uma partida simples no console.
 * O loop permite que cada jogador informe o valor do dado e tome decisões
 * (por exemplo: comprar uma propriedade quando cair nela).
 *
 * Esta é uma implementação simples para apoiar testes manuais e demonstração.
 */
public class Partida {

    private final Tabuleiro tabuleiro;
    private final Jogo jogo;

    public Partida(Tabuleiro tabuleiro, List<Jogador> jogadores) {
        this.tabuleiro = tabuleiro;
        this.jogo = new Jogo(tabuleiro, jogadores);
    }

    public void iniciar() {
        Scanner scanner = new Scanner(System.in);
        List<Jogador> jogadores = new ArrayList<>(this.tabuleiro.getJogadores());
        int turnIndex = 0;

        System.out.println("=== Iniciando partida Cariopoly (console) ===");
        System.out.println("Comandos: digite um número de 1 a 6 representando o dado, ou 'q' para sair.");

        boolean running = true;
        while (running) {
            Jogador atual = jogadores.get(turnIndex);
            System.out.println();
            System.out.println("Vez de: " + atual.getName() + " | Posição: " + atual.getPosition() + " | Saldo: " + atual.getBalance());
            System.out.print("Informe o valor do dado (1-6) ou 'q' para sair: ");
            String entrada = scanner.nextLine().trim();
            if (entrada.equalsIgnoreCase("q")) {
                running = false;
                break;
            }

            int dado;
            try {
                dado = Integer.parseInt(entrada);
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Informe um número entre 1 e 6 ou 'q'.");
                continue;
            }
            if (dado < 1 || dado > 12) { // aceita até 12 caso use dois dados no futuro
                System.out.println("Valor do dado fora do intervalo esperado (1-12). Tente novamente.");
                continue;
            }

            // mover jogador
            atual.mover(dado);
            System.out.println(atual.getName() + " moveu " + dado + " casas até a posição " + atual.getPosition());

            // localizar casa
            Casa casa = encontrarCasaNaPosicao(atual.getPosition());
            if (casa == null) {
                System.out.println("Casa não encontrada no tabuleiro para a posição " + atual.getPosition());
            } else {
                System.out.println("Casa: " + casa.getNome() + " (pos: " + casa.getPosicao() + ")");

                if (casa instanceof Propriedade) {
                    Propriedade prop = (Propriedade) casa;
                    if (prop.getDono() == null) {
                        System.out.println("Esta propriedade está à venda por " + prop.getValorDeCompra());
                        System.out.print("Deseja comprar? (s/n): ");
                        String escolha = scanner.nextLine().trim();
                        if (escolha.equalsIgnoreCase("s")) {
                            boolean comprou = prop.comprar(atual);
                            if (comprou) {
                                System.out.println("Compra realizada. Novo saldo: " + atual.getBalance());
                            } else {
                                System.out.println("Não foi possível comprar (saldo insuficiente ou outro motivo).");
                            }
                        } else {
                            System.out.println("Não comprou a propriedade.");
                        }
                    } else if (!prop.getDono().getId().equals(atual.getId())) {
                        System.out.println("Propriedade pertence a " + prop.getDono().getName() + ". Aluguel: " + prop.getValorDeAluguel());
                        prop.cobrarAluguel(atual);
                        System.out.println("Aluguel cobrado. Saldo atual: " + atual.getBalance());
                    } else {
                        System.out.println("Você caiu na sua própria propriedade.");
                    }
                } else if (casa instanceof Prisao) {
                    System.out.println("Caiu na prisão. Aplicando regra de prisão.");
                    casa.acao(atual);
                    System.out.println("Jogador preso por " + atual.getTurnosRestantesNaPrisão() + " turnos.");
                } else {
                    // casas genéricas
                    casa.acao(atual);
                    System.out.println("Ação da casa executada.");
                }
            }

            // avançar turno
            turnIndex = (turnIndex + 1) % jogadores.size();
        }

        System.out.println("Partida finalizada.");
    }

    private Casa encontrarCasaNaPosicao(int pos) {
        if (this.tabuleiro.getCasas() == null) return null;
        return this.tabuleiro.getCasas().stream()
                .filter(c -> c.getPosicao() == pos)
                .findFirst()
                .orElse(null);
    }

    // Método main para executar a partida localmente (demonstração)
    public static void main(String[] args) {
        // cria jogadores
        Jogador alice = new Jogador("Alice", 0, 1500);
        Jogador bob = new Jogador("Bob", 0, 1500);
        List<Jogador> jogadores = new ArrayList<>();
        jogadores.add(alice);
        jogadores.add(bob);

        // cria algumas casas (posições simples 0..39)
        List<Casa> casas = new ArrayList<>();
        casas.add(new Propriedade("Av. Teste 1", 1, 200, 20));
        casas.add(new Propriedade("Av. Teste 2", 3, 300, 30));
        casas.add(new Prisao("Prisão", 10));
        // preenche casas vazias até 39 para evitar null
        for (int i = 0; i < 40; i++) {
            int pos = i;
            boolean exists = casas.stream().anyMatch(c -> c.getPosicao() == pos);
            if (!exists) {
                casas.add(new Casa("Casa " + pos, pos) {
                    @Override
                    public void acao(Jogador jogador) {
                        // ação padrão: nada
                    }
                });
            }
        }

        Tabuleiro tabuleiro = new Tabuleiro(jogadores, casas);
        Partida partida = new Partida(tabuleiro, jogadores);
        partida.iniciar();
    }
}

