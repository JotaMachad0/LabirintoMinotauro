package LabirintoTest;

import br.com.labirinto.*;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class LabirintoTest {

    private char[][] mapa;
    private Labirinto labirinto;
    private Coordenada entrada;
    private Coordenada saida;
    private Object lock;

    @BeforeEach
    public void setup() {
        mapa = new char[][] {
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

        labirinto = new Labirinto(mapa);   // Cria labirinto com o mapa
        entrada = labirinto.getEntrada();  // Obtém coordenada de entrada 'E'
        saida = labirinto.getSaida();      // Obtém coordenada de saída 'S'
        lock = new Object();                // Objeto para sincronização entre threads
    }

    // Testa se o labirinto tem posições válidas para entrada e saída
    @Test
    @DisplayName("Labirinto tem entrada e saida")
    public void testEntradaESaida() {
        assertNotNull(entrada, "Entrada não pode ser nula");
        assertNotNull(saida, "Saída não pode ser nula");
    }

    // Testa se o jogador consegue encontrar a saída automaticamente
    @Test
    @DisplayName("Jogador encontra a saída")
    public void testJogadorEncontraSaida() {
        Jogador jogador = new Jogador(labirinto, entrada, lock);

        Coordenada posMinotauro = new Coordenada(-1, -1);

        boolean encontrouSaida = false;
        for (int i = 0; i < 100; i++) {  // Limite para evitar loop infinito
            boolean resultado = jogador.moverAutomaticamente(posMinotauro);
            if (!resultado) break;  // Para se não for possível mover mais
            if (jogador.getPosicaoAtual().equals(saida)) {
                encontrouSaida = true; // Encontrou a saída
                break;
            }
        }

        assertTrue(encontrouSaida, "Jogador deve conseguir encontrar a saída");
    }

    // Testa se o Minotauro é criado com uma posição válida no labirinto
    @Test
    @DisplayName("Minotauro foi spawnado")
    public void testMinotauroSpawnado() {
        Coordenada posMinotauro = new Coordenada(1, 1);
        Minotauro minotauro = new Minotauro(labirinto, posMinotauro, null, lock);

        // Verifica se posição do minotauro não é nula
        assertNotNull(minotauro.getPosicaoAtual(), "Minotauro deve ter uma posição inicial");

        // Verifica se posição do minotauro está dentro do labirinto e é válida
        assertTrue(labirinto.ehValido(minotauro.getPosicaoAtual()), "Posição do minotauro deve ser válida");
    }

    // Testa se o jogador deixa um rastro no labirinto ao se mover
    @Test
    @DisplayName("Algoritmo desenha caminho no labirinto")
    public void testAlgoritmoDesenhaCaminho() {
        Jogador jogador = new Jogador(labirinto, entrada, lock);
        Coordenada posMinotauro = new Coordenada(-1, -1);  // Ignora o minotauro nesse teste

        // Força o jogador a mover para desenhar o rastro '.'
        jogador.moverAutomaticamente(posMinotauro);

        // Verifica se há ao menos um '.' no mapa indicando o rastro do jogador
        boolean encontrouRastro = false;
        for (int i = 0; i < mapa.length; i++) {
            for (int j = 0; j < mapa[i].length; j++) {
                if (mapa[i][j] == '.') {
                    encontrouRastro = true;
                    break;
                }
            }
            if (encontrouRastro) break;
        }
        assertTrue(encontrouRastro, "Deve haver um rastro '.' desenhado no labirinto");
    }
}
