package br.com.labirinto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Coordenada {
    private final int linha;    // Armazena o índice da linha da coordenada no labirinto
    private final int coluna;   // Armazena o índice da coluna da coordenada no labirinto

    public Coordenada(int linha, int coluna) {
        this.linha = linha;     // Inicializa a linha
        this.coluna = coluna;   // Inicializa a coluna
    }

    public int getLinha() {
        return linha;           // Retorna a linha da coordenada
    }

    public int getColuna() {
        return coluna;          // Retorna a coluna da coordenada
    }

    // Retorna uma nova Coordenada movida dLinha e dColuna em relação à atual
    public Coordenada mover(int dLinha, int dColuna) {
        return new Coordenada(linha + dLinha, coluna + dColuna);
    }

    // Retorna as coordenadas vizinhas diretas (cima, baixo, esquerda, direita)
    public List<Coordenada> vizinhos() {
        List<Coordenada> lista = new ArrayList<>();
        lista.add(new Coordenada(linha - 1, coluna)); // cima
        lista.add(new Coordenada(linha + 1, coluna)); // baixo
        lista.add(new Coordenada(linha, coluna - 1)); // esquerda
        lista.add(new Coordenada(linha, coluna + 1)); // direita
        return lista;  // lista com as 4 coordenadas adjacentes
    }

    // Metodo para comparar se duas coordenadas são iguais (mesma linha e coluna)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;               // Se o mesmo objeto, já é igual
        if (!(o instanceof Coordenada)) return false;  // Se não for Coordenada, não é igual
        Coordenada c = (Coordenada) o;
        return linha == c.linha && coluna == c.coluna; // Compara linha e coluna
    }

    // Gera hashCode baseado em linha e coluna para uso em coleções
    @Override
    public int hashCode() {
        return Objects.hash(linha, coluna);
    }
}
