🏛️ Desafio: O Labirinto do Minotauro

Você precisa desenvolver um programa em Java que simula um personagem tentando escapar de um labirinto representado por caracteres ASCII. O personagem inicia sua jornada em uma posição marcada como E (entrada) e tenta alcançar a saída, marcada como S. Durante sua travessia, o personagem deixa rastros (.) pelo caminho percorrido.
Neste labirinto, habita um minotauro perigoso (M). O minotauro inicialmente não está visível. Porém, após uma quantidade fixa de passos dados pelo personagem (essa quantidade é um valor inteiro X, fornecido na entrada), o minotauro aparece repentinamente em uma posição aleatória do labirinto. A cada X passos subsequentes, o minotauro muda novamente de lugar, sempre ocupando uma posição livre aleatória ( ).
Se, em qualquer momento, o personagem e o minotauro se encontrarem (ou seja, ocuparem a mesma posição), a tentativa de escapar falha imediatamente.


📌 Detalhes do Labirinto



O labirinto é uma matriz ASCII de tamanho variável, contendo:


#: Parede (não pode atravessar)

 : Espaço livre (pode atravessar)

E: Entrada (ponto inicial do personagem)

S: Saída (objetivo final)

.: Caminho percorrido pelo personagem

M: Minotauro (posição dinâmica)





🚧 Funcionamento Esperado



Receber na entrada:

O labirinto ASCII inicial (tamanho variável).
Um inteiro positivo X, representando o número de passos do personagem após os quais o minotauro muda de lugar.



Utilizar um algoritmo de busca (ex.: BFS ou DFS) para encontrar um caminho da entrada até a saída.


Após o personagem dar exatamente X passos, uma thread separada move o minotauro para uma nova posição aleatória vazia ( ).


Se o personagem ocupar a mesma posição do minotauro a qualquer instante, o personagem falha e o programa termina com uma mensagem de derrota.


Se o personagem chegar à saída (S) sem encontrar o minotauro, o programa termina com uma mensagem de vitória e exibe o labirinto com o caminho percorrido.




📍 Exemplo de Entrada

Considere X = 5 passos:

5
##########
#E #     #
#  # ### #
## # # S #
#     #  #
##########


(o primeiro número é o valor X, seguido pelo labirinto)


📍 Exemplo de Saída (Vitória)


Vitória! O personagem escapou com sucesso!

##########
#E.#.....#
#. #.###.#
##.#.# S.#
#.....#  #
##########




📍 Exemplo de Saída (Derrota)


Derrota! O personagem encontrou o Minotauro!

##########
#E.#..M  #
#. # ### #
##.# # S #
#     #  #
##########




🛠️ Orientações Técnicas


Utilize a API de Threads e sincronização do Java.
Garanta que o acesso ao labirinto seja seguro para múltiplas threads simultâneas.
Controle a aparição e movimentação do minotauro através de um contador compartilhado de passos do personagem.
O minotauro jamais deve surgir em cima das paredes ou da saída.
Prefira algoritmos bem estruturados e eficientes de busca de caminho.



#####
#E  #
# # #
#  S#
#####



#######
#E #  #
#  ## #
##    #
# ## ##
#S    #
#######



##########
#E#      #
# # #### #
# # #  # #
#   ## # #
###    # #
#   ###  #
# #   ####
#   #   S#
##########



############
#E#        #
# # #### # #
# #    # # #
# ### ## # #
#   #    # #
# # ###### #
# #      # #
# ### ##   #
#     #### #
#   #    S #
############



####################
#E     #      #    #
# #### # #### # ## #
# #    #    # # #  #
# # ###### # # # ###
# #      # # #     #
# ###### # # ##### #
#        # #   #   #
######## # ### # # #
#      # #     #S  #
####################



#########################
#E      #     #         #
### ### ### # # ####### #
#     #     # #       # #
# ##### ##### ####### # #
#     #       #     # # #
##### # ####### ### # # #
#     #         #   #   #
# ########### # ####### #
#             #       # #
############### ##### # #
#                   # S #
#########################




Implementação correta do movimento do minotauro em resposta aos passos.
Implementação robusta do algoritmo de busca do personagem.
Uso apropriado de threads e técnicas de sincronização.
Código limpo, legível e organizado.
Robustez e eficiência geral da solução.


Boa sorte e cuidado com o Minotauro!
