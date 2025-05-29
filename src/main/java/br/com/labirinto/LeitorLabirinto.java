package br.com.labirinto;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LeitorLabirinto {

    // Classe estática interna que encapsula os dados de entrada do labirinto:
    // número de passos para mover o Minotauro e o objeto Labirinto carregado
    public static class EntradaLabirinto {
        public final int passosParaMoverMinotauro;  // Número X de passos para movimentar o Minotauro
        public final Labirinto labirinto;

        // Construtor que recebe o número de passos e o labirinto criado
        public EntradaLabirinto(int passos, Labirinto labirinto) {
            this.passosParaMoverMinotauro = passos;
            this.labirinto = labirinto;
        }
    }

    //  lê a entrada do labirinto a partir de um Scanner
    // Espera na primeira linha um inteiro (X), depois linhas do labirinto em formato texto
    public static EntradaLabirinto lerLabirinto(Scanner scanner) {
        int passos = Integer.parseInt(scanner.nextLine().trim()); // Lê número X da primeira linha

        List<char[]> linhas = new ArrayList<>();  // Lista para armazenar cada linha do labirinto
        while (scanner.hasNextLine()) {
            String linha = scanner.nextLine();    // Lê a próxima linha do labirinto como String
            linhas.add(linha.toCharArray());      // Converte a linha para array de chars e adiciona à lista
        }

        // Converte a lista de arrays de chars para uma matriz
        char[][] matriz = linhas.toArray(new char[0][]);

        // Cria o objeto Labirinto a partir da matriz
        Labirinto labirinto = new Labirinto(matriz);

        // Retorna uma instância de EntradaLabirinto contendo passos e labirinto
        return new EntradaLabirinto(passos, labirinto);
    }
}
