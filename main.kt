import kotlin.io.readln;
import kotlin.random.Random

val jogadores = mutableMapOf<String, Map<String, Any>>()
var nomeJogador1: String = "PARTICIPANTE01"
var nomeJogador2: String = "PARTICIPANTE02"
val cores = listOf(
    "\u001B[31m", // Vermelho
    "\u001B[34m", // Azul
    "\u001B[33m", // Amarelo
    "\u001B[30m"  // Preto
)

val reset = "\u001B[0m"

// Matheus
fun main() {
    println(""" 
        ==============================================
                   MANGA ROSA MEMORY GAME
        ==============================================
          1. INICIAR
          2. PONTUAÇÃO PARTICIPANTES
          3. REGRAS DO JOGO
          4. SAIR
        ==============================================
          INFORME SUA OPÇÃO:
    """)

    val opcaoMenu = readln().toint()
    when (opcaoMenu) {
        1 -> {
            println("\n=======================================")
            println("       INICIANDO O JOGO...       ")
            println("\n=======================================")
            
            iniciarJogo()
        }
        2 -> {
            println("\n=======================================")
            println("       PONTUAÇÃO DOS PARTICIPANTES...      ")
            println("\n=======================================")

            exibirPontuacao()
       }
       3 -> {
            println("\n==============================")
            println("       REGRAS DO JOGO...      ")
            println("\n==============================")

            exibirRegras()
       }
       4 -> {
            println("\n=======================================")
            println("       SAINDO DO JOGO... ATÉ LOGO      ")
            println("\n=======================================")
            break
       }
       else -> {
        println("\n==========================================")
        println("       OPÇÃO INVÁLIDA! TENTE NOVAMENTE      ")
        println("\n==========================================")
       }

    }
 
}

// Henrique
fun iniciarJogo() {
    val (linhas, colunas) = capturarTamanhoTabuleiro()
    val tabuleiro = criarTabuleiro(linhas, colunas)
    val revelados = mutableSetOf<Pair<Int, Int>>()
    val paresEncontrados = mutableSetOf<String>()
    var ultimaEscolha: Pair<Int, Int>? = null

    while (paresEncontrados.size < (linhas * colunas) / 2) {
        exibirPontuacao();
        exibirTabuleiro(tabuleiro, revelados)
        print("Escolha uma linha: ")
        var linha = readln().toInt()
        linha -= 1
        print("Escolha uma coluna: ")
        var coluna = readln().toInt()
        coluna -= 1
    
        if (linha in 0 until linhas && coluna in 0 until colunas) {
            revelados.add(linha to coluna)
            exibirTabuleiro(tabuleiro, revelados)
    
            if (ultimaEscolha == null) {
                ultimaEscolha = linha to coluna
            } else {
                val (linhaAnterior, colunaAnterior) = ultimaEscolha
                val valorAnterior = tabuleiro[linhaAnterior][colunaAnterior].second
                val valorAtual = tabuleiro[linha][coluna].second
    
                if (valorAtual == valorAnterior) {
                    paresEncontrados.add(valorAtual)
                } else {
                    println("Pares errados!")
                    println("==============================================")
                    Thread.sleep(2000) // Aguarda 2 segundos antes de resetar
                    revelados.remove(ultimaEscolha)
                    revelados.remove(linha to coluna)
                    
                }
                ultimaEscolha = null
            }
        } else {
            println("Posição inválida!")
        }
    }
    println("Parabéns! Você encontrou todos os pares!")
}

// Arthur
fun capturarTamanhoTabuleiro(): Pair<Int, Int> {
    // Aqui você pode adicionar a lógica para capturar o tamanho do tabuleiro que o jogador deseja jogar.   
    // Retorne em Pair a quantidade de linhas e colunas do tabuleiro.
    // Se n souber, pesquise sobre Pair em Kotlin.
    // Exemplo de retorno: Pair(4, 4)
    return Pair(4, 4)
}

// Arthur
fun config() {
    // Aqui vc pode adicionar a lógica de configuração do jogo, como escolha de cores, nomes dos jogadores, etc.


     /*  Assim q salva as configurações dos jogadores no Map
     jogadores[nomeJogador1.ifBlank { "PARTICIPANTE01" }] = mapOf("cor" to cor1, "pontuacao" to 0)
     jogadores[nomeJogador2.ifBlank { "PARTICIPANTE02" }] = mapOf("cor" to cor2, "pontuacao" to 0)*/

}

// Laysa
fun exibirPontuacao() {
    // Aqui você pode adicionar a lógica para exibir a pontuação dos jogadores.
    // Utilize o map jogadores para acessar os dados dos jogadores.
}

// Matheus
fun exibirRegras() {
    println("""
    1. No início do jogo, o participante deve escolher o tamanho do tabuleiro:
    Opções: 4x4, 6x6, 8x8 ou 10x10.
    2. Todos os pares de figuras possuem uma cor de fundo: vermelho, azul, amarelo ou preto.
    3. Em todo jogo deve existir uma figura de fundo preto.
    4. Em todo jogo, metade das figuras devem ter fundo azul e vermelho.
    5. As demais figuras que sobram no jogo devem ter fundo amarelo.
    6. Cada participante deve ter atribuído a si uma cor (vermelho ou azul) no início do jogo.
    7. Todo participante deve ter um nome registrado. Caso não tenha, o nome padrão será atribuído:
    'PARTICIPANTE01' e 'PARTICIPANTE02'.
    8. Cada participante possui uma pontuação atrelada a si.
    9. Se o participante encontrar um par de cartas com fundo amarelo, ele(a) fatura 1 ponto.
    10. Se o participante encontrar um par de cartas com o fundo da sua cor, ele(a) fatura 5 pontos.
    11. Se o participante encontrar um par de cartas com o fundo da cor de seu adversário e errar, perde 2 pontos.
    Porém, se acertar, ganha apenas 1 ponto.
    12. O participante não pode ter pontuação negativa. Se perder mais pontos do que possui, ficará com a pontuação zerada.
    13. Se o participante encontrar uma carta com fundo preto e errar o seu par, perde o jogo, mesmo que tenha a pontuação superior à do adversário.
    Mas se acertar, ganha o jogo.
    ===========================================================================================================
    REGRAS DE INTERAÇÃO DURANTE O JOGO:
    14. Se o participante informar uma opção de tamanho de tabuleiro inválida, será exibida a seguinte mensagem:
    'Por favor, escolha uma das opções de tamanho de tabuleiro disponíveis'.
    15. O participante deve informar a linha e a coluna da carta que deseja virar.
    16. Caso os valores de linha e coluna sejam inválidos, será exibida a seguinte mensagem:
    'Posição da carta inválida, por favor, insira uma posição válida'.
    O participante terá 3 tentativas para corrigir a posição, caso erre 3 vezes, perde a vez.
    17. Se o participante escolher uma carta já virada, será exibida a mensagem:
    'A carta da posição informada já está virada, por favor, escolha outra posição'.
    O participante terá 3 tentativas para corrigir, caso erre 3 vezes, perde a vez.
    ===========================================================================================================""")
}

// Laysa
fun atualizarPontuacao(jogador: String, pontos: Int) {
    // Aqui você pode adicionar a lógica para atualizar a pontuação do jogador.
    // Utilize o map jogadores para acessar os dados dos jogadores e atualiza-los.
}

// Henrique
fun criarTabuleiro(linhas: Int, colunas: Int): Array<Array<Pair<String, String>>> {
    val totalCartas = (linhas * colunas) / 2
    val letras = ('A'..'Z').toList()
    val pares = (1..totalCartas).map { "${it}${letras.random()}" }.flatMap { listOf(it, it) }.shuffled()
    val coresAssociadas = atribuirCores(pares.distinct())

    val tabuleiro = Array(linhas) { Array(colunas) { Pair("", "") } }

    var index = 0
    for (i in 0 until linhas) {
        for (j in 0 until colunas) {
            val valor = pares[index]
            val cor = coresAssociadas[valor] ?: reset
            tabuleiro[i][j] = Pair(cor, valor)
            index++
        }
    }
    return tabuleiro
}

// Henrique
fun exibirTabuleiro(tabuleiro: Array<Array<Pair<String, String>>>, revelados: Set<Pair<Int, Int>>) {
    
    for (i in tabuleiro.indices) {
        if (i == 0){
            var num:String = "";
            for (j in 1..tabuleiro.size){
                num += "----${j}----";
            }
            print("\n---"); 
            println("${num}"); 
        }  
        print("${i+1}")

        for (j in tabuleiro[i].indices) {
            val (cor, valor) = tabuleiro[i][j]
            val simbolo = if (revelados.contains(i to j)) "$cor■■$valor■■$reset" else "■■??■■"
            print(" | $simbolo")
        }
        println(" |")
        println("--------".repeat((tabuleiro.size + 1)))
    }
}

// Arthur
fun atribuirCores(pares: List<String>): Map<String, String> {
    // Aqui você pode adicionar a lógica para atribuir cores as cartas do tabuleiro.
    // Retorne um Map com as cartas e suas respectivas cores.
    // Exemplo de retorno: mapOf("A1" to "\u001B[31m", "A2" to "\u001B[34m", "B1" to "\u001B[33m", "B2" to "\u001B[30m") 
    // A cor preta é obrigatória, as demais cores podem ser escolhidas aleatoriamente.
    // Utilize a lista cores para escolher as cores.
    // Exemplo de uso: cores[0] para pegar a cor vermelha.
    // Exemplo de uso: cores[1] para pegar a cor azul.
    // Exemplo de uso: cores[2] para pegar a cor amarela.
    // Exemplo de uso: cores[3] para pegar a cor preta.
    // Utilize a função shuffled() para embaralhar a lista de pares.
    // Exemplo de uso: cores.shuffled() para embaralhar a lista de cores.
    val coresMap = mutableMapOf<String, String>()
    
    
    return coresMap
}
