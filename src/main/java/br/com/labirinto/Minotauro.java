package br.com.labirinto;

public class Minotauro {
    private Coordenada posicaoAtual;     // Posição atual do Minotauro no labirinto
    private final Labirinto labirinto;   // Referência ao labirinto para verificar posições e mapa
    private final Jogador jogador;       // Referência ao jogador para eventualmente reagir a ele
    private final Object lock;            // Objeto usado para sincronização entre threads

    // Construtor que inicializa o Minotauro no labirinto, posição inicial, jogador e o lock de sincronização
    public Minotauro(Labirinto labirinto, Coordenada posicaoInicial, Jogador jogador, Object lock) {
        this.labirinto = labirinto;
        this.posicaoAtual = posicaoInicial;
        this.jogador = jogador;
        this.lock = lock;
    }

    // Metodo para obter a posição atual do Minotauro de forma thread-safe
    public Coordenada getPosicaoAtual() {
        synchronized (lock) {  // Garante que a leitura da posição seja sincronizada entre threads
            return posicaoAtual;
        }
    }

    // Metodo para atualizar a posição atual do Minotauro de forma thread-safe
    public void setPosicaoAtual(Coordenada novaPosicao) {
        synchronized (lock) {  // Garante que a escrita da posição seja sincronizada
            this.posicaoAtual = novaPosicao;
        }
    }
}
