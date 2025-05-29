package br.com.labirinto;

import java.util.*;

public class Jogador {
    private final Labirinto labirinto;          // Referência ao labirinto onde o jogador está
    private Coordenada posicaoAtual;             // Posição atual do jogador no labirinto
    private Coordenada posicaoAnterior;          // Posição anterior do jogador, usada para deixar rastro
    private final Object lock;                    // Objeto para sincronização em ambientes concorrentes

    public Jogador(Labirinto labirinto, Coordenada posicaoInicial, Object lock) {
        this.labirinto = labirinto;
        this.posicaoAtual = posicaoInicial;
        this.posicaoAnterior = posicaoInicial;
        this.lock = lock;
        labirinto.setPosicaoPersonagem(posicaoAtual, 'J'); // Marca a posição inicial do jogador no labirinto
    }

    public Coordenada getPosicaoAtual() {
        return posicaoAtual;                       // Retorna a posição atual do jogador
    }

    public Coordenada getPosicaoAnterior() {
        return posicaoAnterior;                    // Retorna a posição anterior do jogador
    }

    // Metodo principal que move o jogador automaticamente em direção à saída,
    // evitando o Minotauro (posiçãoMinotauro)
    public boolean moverAutomaticamente(Coordenada posicaoMinotauro) {
        Coordenada saida = labirinto.getSaida();

        // Se já está na saída, jogador venceu
        if (posicaoAtual.equals(saida)) {
            return true; // Vitória
        }

        // Se jogador e minotauro estão na mesma posição, o jogador perde
        if (posicaoAtual.equals(posicaoMinotauro)) {
            System.out.println("Jogador foi pego pelo Minotauro!");
            return false; // Derrota
        }

        // Busca caminho seguro da posição atual até a saída, evitando o Minotauro
        List<Coordenada> caminho = buscarCaminhoBFS(posicaoAtual, saida, posicaoMinotauro);

        // Se não há caminho ou o caminho não tem próximos passos, jogador está encurralado
        if (caminho == null || caminho.size() < 2) {
            System.out.println("Jogador ficou encurralado! Não há caminho seguro.");
            return false; // Derrota
        }

        Coordenada proximo = caminho.get(1); // Próximo passo a dar (índice 0 é posição atual)

        // Se próximo passo é a posição do Minotauro, jogador perde
        if (proximo.equals(posicaoMinotauro)) {
            System.out.println("Próximo passo é onde está o Minotauro!");
            return false; // Derrota
        }

        // Atualiza posição do jogador no labirinto de forma sincronizada (
        synchronized (lock) {
            if (!labirinto.ehValido(proximo)) return false;

            labirinto.setPosicaoPersonagem(posicaoAtual, '.'); // Deixa rastro na posição antiga
            posicaoAnterior = posicaoAtual;
            posicaoAtual = proximo;
            labirinto.setPosicaoPersonagem(posicaoAtual, 'J'); // Marca nova posição
        }

        return true; // continua no jogo
    }

    // Busca o caminho seguro até a saída usando BFS, evitando o Minotauro
    private List<Coordenada> buscarCaminhoBFS(Coordenada inicio, Coordenada destino, Coordenada minotauro) {
        Queue<Coordenada> fila = new LinkedList<>();
        Map<Coordenada, Coordenada> antecessor = new HashMap<>(); // Para reconstruir caminho
        Set<Coordenada> visitados = new HashSet<>();

        fila.add(inicio);
        visitados.add(inicio);

        while (!fila.isEmpty()) {
            Coordenada atual = fila.poll();

            if (atual.equals(destino)) {
                // Reconstrói o caminho do destino até o início
                List<Coordenada> caminho = new ArrayList<>();
                for (Coordenada passo = destino; passo != null; passo = antecessor.get(passo)) {
                    caminho.add(passo);
                }
                Collections.reverse(caminho); // Inverte para ter caminho do início ao destino
                return caminho;
            }

            // Explora os vizinhos válidos, não visitados, sem paredes e que não são posição do Minotauro
            for (Coordenada vizinho : labirinto.getVizinhos(atual)) {
                if (!visitados.contains(vizinho)
                        && labirinto.ehValido(vizinho)
                        && labirinto.getValorPosicao(vizinho) != '#'
                        && !vizinho.equals(minotauro)) {
                    visitados.add(vizinho);
                    antecessor.put(vizinho, atual);
                    fila.add(vizinho);
                }
            }
        }

        return null; // Nenhum caminho seguro encontrado
    }
}
