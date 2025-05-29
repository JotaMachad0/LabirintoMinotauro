package br.com.labirinto;

import java.util.ArrayList;
import java.util.List;

public class Labirinto {
    private final char[][] mapa;               // Matriz que representa o mapa do labirinto
    private final Coordenada entrada;          // Coordenada da posição de entrada 'E'
    private final Coordenada saida;            // Coordenada da posição de saída 'S'

    // Construtor recebe o mapa como matriz de chars e define entrada e saída
    public Labirinto(char[][] mapa) {
        this.mapa = mapa;
        this.entrada = encontrarEntrada();    // Localiza posição da entrada no mapa
        this.saida = encontrarSaida();        // Localiza posição da saída no mapa
    }

    // Percorre o mapa procurando a letra 'E' para encontrar a entrada
    private Coordenada encontrarEntrada() {
        for (int i = 0; i < mapa.length; i++) {
            for (int j = 0; j < mapa[i].length; j++) {
                if (mapa[i][j] == 'E') {
                    return new Coordenada(i, j);  // Retorna coordenada da entrada encontrada
                }
            }
        }
        return null;  // Retorna null caso não encontre a entrada
    }

    // Percorre o mapa procurando a letra 'S' para encontrar a saída
    private Coordenada encontrarSaida() {
        for (int i = 0; i < mapa.length; i++) {
            for (int j = 0; j < mapa[i].length; j++) {
                if (mapa[i][j] == 'S') {
                    return new Coordenada(i, j);  // Retorna coordenada da saída encontrada
                }
            }
        }
        return null;  // Retorna null caso não encontre a saída
    }

    public Coordenada getEntrada() {
        return entrada;  // Retorna a coordenada da entrada
    }

    public Coordenada getSaida() {
        return saida;    // Retorna a coordenada da saída
    }

    public int getAltura() {
        return mapa.length;  // Retorna número de linhas do mapa
    }

    public int getLargura() {
        return mapa[0].length;  // Retorna número de colunas do mapa
    }

    // Verifica se a posição fornecida é válida
    public boolean ehValido(Coordenada pos) {
        int l = pos.getLinha();
        int c = pos.getColuna();
        return l >= 0 && l < mapa.length && c >= 0 && c < mapa[0].length && mapa[l][c] != '#';
    }

    // Retorna o caractere que está na posição indicada do mapa
    public char getValorPosicao(Coordenada pos) {
        return mapa[pos.getLinha()][pos.getColuna()];
    }

    // Retorna uma lista com as coordenadas vizinhas (cima, baixo, esquerda, direita) da posição dada
    public List<Coordenada> getVizinhos(Coordenada pos) {
        List<Coordenada> vizinhos = new ArrayList<>();
        int l = pos.getLinha();
        int c = pos.getColuna();

        // Adiciona coordenada de cima, se estiver dentro do mapa
        if (l - 1 >= 0) vizinhos.add(new Coordenada(l - 1, c));
        // Adiciona coordenada de baixo, se estiver dentro do mapa
        if (l + 1 < mapa.length) vizinhos.add(new Coordenada(l + 1, c));
        // Adiciona coordenada da esquerda, se estiver dentro do mapa
        if (c - 1 >= 0) vizinhos.add(new Coordenada(l, c - 1));
        // Adiciona coordenada da direita, se estiver dentro do mapa
        if (c + 1 < mapa[0].length) vizinhos.add(new Coordenada(l, c + 1));

        return vizinhos;  // Retorna lista de vizinhos
    }

    // Altera o caractere da posição no mapa para o valor informado
    // NÃO altera a posição da saída 'S'
    public void setPosicaoPersonagem(Coordenada pos, char valor) {
        if (mapa[pos.getLinha()][pos.getColuna()] != 'S') {
            mapa[pos.getLinha()][pos.getColuna()] = valor;
        }
    }

    // Retorna uma lista com todas as posições válidas (não parede) do mapa
    public List<Coordenada> getTodasPosicoesValidas() {
        List<Coordenada> validas = new ArrayList<>();
        for (int i = 0; i < mapa.length; i++) {
            for (int j = 0; j < mapa[i].length; j++) {
                if (mapa[i][j] != '#') {
                    validas.add(new Coordenada(i, j));
                }
            }
        }
        return validas;
    }

    // Imprime o mapa, mostrando o jogador 'J' e o Minotauro 'M' nas posições atuais
    // Caso contrário, imprime o caractere original do mapa
    public void imprimirMapa(Coordenada posJogador, Coordenada posMinotauro) {
        for (int i = 0; i < mapa.length; i++) {
            for (int j = 0; j < mapa[i].length; j++) {
                Coordenada atual = new Coordenada(i, j);
                if (atual.equals(posJogador)) {
                    System.out.print('J');  // Mostra jogador
                } else if (atual.equals(posMinotauro)) {
                    System.out.print('M');  // Mostra Minotauro
                } else {
                    System.out.print(mapa[i][j]);  // Mostra o caractere original do mapa
                }
            }
            System.out.println();  // Quebra de linha após cada linha do mapa
        }
    }
}
