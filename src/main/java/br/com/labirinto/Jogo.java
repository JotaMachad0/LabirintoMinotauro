package br.com.labirinto;

import java.util.List;
import java.util.Random;

public class Jogo {
    private final Labirinto labirinto;                 // Referência para o labirinto do jogo
    private final Jogador jogador;                       // Objeto jogador que se move no labirinto
    private final Minotauro minotauro;                   // Objeto Minotauro que persegue o jogador
    private final int passosParaMoverMinotauro;          // Número de passos do jogador para o Minotauro se mover
    private final Object lock;                            // Objeto para sincronização entre threads

    private volatile boolean jogoAtivo = true;           // Flag para controle se o jogo está ativo
    private int contadorPassosJogador = 0;               // Contador de passos dados pelo jogador
    private final Random random = new Random();           // Gerador de números aleatórios para teleporte do Minotauro

    // Construtor inicializa os componentes do jogo e parâmetros
    public Jogo(Labirinto labirinto, Jogador jogador, Minotauro minotauro, int passosParaMoverMinotauro, Object lock) {
        this.labirinto = labirinto;
        this.jogador = jogador;
        this.minotauro = minotauro;
        this.passosParaMoverMinotauro = passosParaMoverMinotauro;
        this.lock = lock;
    }

    // Método que inicia a execução do jogo
    public void iniciar() {
        // Cria uma thread separada para controlar o teleporte do Minotauro
        Thread threadMinotauro = new Thread(() -> {
            while (jogoAtivo) {                          // Enquanto o jogo estiver ativo
                try {
                    synchronized (lock) {                // Sincroniza acesso aos dados compartilhados
                        if (contadorPassosJogador >= passosParaMoverMinotauro) { // Verifica se deve mover Minotauro
                            teleportarMinotauroAleatorio();  // Teleporta Minotauro para nova posição aleatória
                            contadorPassosJogador = 0;       // Reseta contador de passos
                        }
                    }
                    Thread.sleep(300);                    // Aguarda 300 ms antes de nova verificação
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();  // Reativa interrupção da thread e encerra loop
                    return;
                }
            }
        });

        threadMinotauro.start();                        // Inicia a thread do Minotauro

        while (jogoAtivo) {                             // Loop principal do jogo
            synchronized (lock) {                        // Sincroniza para evitar conflitos de acesso
                boolean moveu = jogador.moverAutomaticamente(minotauro.getPosicaoAtual()); // Move o jogador automaticamente evitando Minotauro

                if (jogador.getPosicaoAtual().equals(labirinto.getSaida())) {  // Se jogador chegou na saída
                    System.out.println("Você escapou com sucesso!");
                    jogoAtivo = false;                   // Finaliza o jogo
                    break;
                }

                if (!moveu) {                            // Se jogador não conseguiu se mover (preso)
                    System.out.println("Jogador não pode se mover. Fim do jogo.");
                    jogoAtivo = false;                   // Finaliza o jogo
                    break;
                }

                contadorPassosJogador++;                 // Incrementa contador de passos do jogador
                atualizarMapa();                         // Atualiza e imprime o mapa atual

                if (jogador.getPosicaoAtual().equals(minotauro.getPosicaoAtual())) { // Se Minotauro pegou o jogador
                    System.out.println("O Minotauro pegou você! Fim do jogo.");
                    jogoAtivo = false;                   // Finaliza o jogo
                    break;
                }
            }

            try {
                Thread.sleep(500);                        // Aguarda 500 ms entre movimentos do jogador
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();      // Reativa interrupção e sai do loop
                break;
            }
        }

        synchronized (lock) {                            // Sincroniza para atualizar mapa na finalização
            atualizarMapa();
        }

        try {
            threadMinotauro.join();                       // Aguarda a thread do Minotauro terminar antes de encerrar
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();          // Reativa interrupção se ocorrer durante o join
        }
    }

    // Teleporta o Minotauro para uma posição válida aleatória diferente da posição do jogador
    private void teleportarMinotauroAleatorio() {
        List<Coordenada> posicoesValidas = labirinto.getTodasPosicoesValidas();  // Obtém lista de posições válidas no labirinto
        if (posicoesValidas.isEmpty()) {                   // Caso não existam posições válidas
            System.out.println("Não há posições válidas para o Minotauro teleportar.");
            return;                                        // Sai do metodo
        }

        Coordenada novaPosicao;
        do {
            novaPosicao = posicoesValidas.get(random.nextInt(posicoesValidas.size())); // Seleciona posição aleatória
        } while (novaPosicao.equals(jogador.getPosicaoAtual()));  // Garante que não será a posição do jogador

        minotauro.setPosicaoAtual(novaPosicao);              // Atualiza posição do Minotauro
        System.out.println("Minotauro teleportado para: (" + novaPosicao.getLinha() + ", " + novaPosicao.getColuna() + ")");
    }

    // Atualiza o mapa imprimindo a posição atual do jogador e minotauro e marca o rastro do jogador
    private void atualizarMapa() {
        Coordenada anterior = jogador.getPosicaoAnterior();   // Obtém posição anterior do jogador

        if (!anterior.equals(labirinto.getSaida())) {         // Se a posição anterior não é a saída
            labirinto.setPosicaoPersonagem(anterior, '.');    // Marca o caminho do jogador com '.'
        }

        // Imprime o mapa com jogador e minotauro nas posições atuais
        labirinto.imprimirMapa(jogador.getPosicaoAtual(), minotauro.getPosicaoAtual());
    }
}
