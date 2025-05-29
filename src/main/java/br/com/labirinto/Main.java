
package br.com.labirinto;

import java.util.Random;

public class Main {

    public static void main(String[] args) {
        int passosParaMoverMinotauro = 5; // Define quantos passos o jogador precisa dar para o Minotauro se mover ( pode colocar mais ou menos )

        // Define o mapa do labirinto como um array de linhas (char arrays)
        char[][] mapa = {
                "#########################".toCharArray(),
                "#E      #     #         #".toCharArray(),
                "### ### ### # # ####### #".toCharArray(),
                "#     #     # #       # #".toCharArray(),
                "# ##### ##### ####### # #".toCharArray(),
                "#     #       #     # # #".toCharArray(),
                "##### # ####### ### # # #".toCharArray(),
                "#     #         #   #   #".toCharArray(),
                "# ########### # ####### #".toCharArray(),
                "#             #       # #".toCharArray(),
                "############### ##### # #".toCharArray(),
                "#                   # S #".toCharArray(),
                "#########################".toCharArray()
        };

        Labirinto labirinto = new Labirinto(mapa);          // Cria o labirinto com o mapa definido
        Coordenada entrada = labirinto.getEntrada();        // Pega a coordenada da entrada 'E'
        Coordenada saida = labirinto.getSaida();            // Pega a coordenada da saída 'S'

        Coordenada posicaoJogador = entrada;                 // Inicializa a posição do jogador na entrada
        // Gera uma posição aleatória válida para o Minotauro (não pode ser entrada ou saída)
        Coordenada posicaoMinotauro = gerarPosicaoAleatoriaValida(labirinto, entrada, saida);

        Object lock = new Object();                           // Objeto usado para sincronização entre threads

        Jogador jogador = new Jogador(labirinto, posicaoJogador, lock);   // Cria o jogador
        Minotauro minotauro = new Minotauro(labirinto, posicaoMinotauro, jogador, lock); // Cria o minotauro

        Jogo jogo = new Jogo(labirinto, jogador, minotauro, passosParaMoverMinotauro, lock); // Cria o jogo
        jogo.iniciar();                                       // Inicia o loop do jogo (jogador + minotauro)
    }

    // Metodo que gera uma coordenada válida aleatória dentro do labirinto,
    // que não seja a entrada nem a saída, e que não seja parede
    private static Coordenada gerarPosicaoAleatoriaValida(Labirinto labirinto, Coordenada entrada, Coordenada saida) {
        Random random = new Random();
        int altura = labirinto.getAltura();
        int largura = labirinto.getLargura();

        Coordenada tentativa;
        do {
            int linha = random.nextInt(altura);    // Escolhe linha aleatória
            int coluna = random.nextInt(largura);  // Escolhe coluna aleatória
            tentativa = new Coordenada(linha, coluna);
            // Repete enquanto a posição não for válida
        } while (!labirinto.ehValido(tentativa) || tentativa.equals(entrada) || tentativa.equals(saida));

        return tentativa; // Retorna a posição válida encontrada
    }
}
