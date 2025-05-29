package br.com.labirinto;

import java.util.ArrayList;
import java.util.List;

public class Caminho {

    // Matriz de direções para mover no labirinto: cima, baixo, esquerda, direita
    private static final int[][] DIRECOES = {
            {-1, 0}, // cima
            {1, 0},  // baixo
            {0, -1}, // esquerda
            {0, 1}   // direita
    };

    // Metodo principal para encontrar um caminho do início ao fim no labirinto
    public static List<Coordenada> encontrarCaminho(Labirinto labirinto) {
        // Cria matriz para marcar as posições já visitadas no labirinto
        boolean[][] visitado = new boolean[labirinto.getAltura()][labirinto.getLargura()];
        // Lista que armazenará o caminho válido encontrado
        List<Coordenada> caminho = new ArrayList<>();

        // Obtém as coordenadas da entrada e da saída do labirinto
        Coordenada inicio = labirinto.getEntrada();
        Coordenada fim = labirinto.getSaida();

        //  (DFS) para encontrar o caminho
        if (dfs(labirinto, visitado, inicio.getLinha(), inicio.getColuna(), caminho, fim)) {
            return caminho; // retorna caminho encontrado
        }

        return null; // retorna null se nenhum caminho foi encontrado
    }

    // Busca para explorar caminhos possíveis
    private static boolean dfs(Labirinto labirinto, boolean[][] visitado, int linha, int coluna,
                               List<Coordenada> caminho, Coordenada fim) {

        Coordenada atual = new Coordenada(linha, coluna);

        // Verifica se a posição atual é válida e não foi visitada antes
        if (!labirinto.ehValido(atual) || visitado[linha][coluna]) {
            return false; // posição inválida ou já visitada
        }

        // Marca a posição atual como visitada e adiciona ao caminho
        visitado[linha][coluna] = true;
        caminho.add(atual);

        // Se chegou na saída, encontrou caminho válido
        if (atual.equals(fim)) {
            return true;
        }

        // Tenta se mover nas 4 direções (cima, baixo, esquerda, direita)
        for (int[] d : DIRECOES) {
            int novaLinha = linha + d[0];
            int novaColuna = coluna + d[1];

            // Se alguma chamada recursiva encontrar caminho válido, retorna true
            if (dfs(labirinto, visitado, novaLinha, novaColuna, caminho, fim)) {
                return true;
            }
        }

        // Se nenhum caminho válido foi encontrado nas direções, remove a posição atual
        // do caminho (backtracking) e retorna false
        caminho.remove(caminho.size() - 1);
        return false;
    }
}
