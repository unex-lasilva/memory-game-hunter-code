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
    // Nessa função você pode adicionar a lógica de menu principal do jogo, como iniciar o jogo, exibir pontuações, regras, e as demais opções, chamando seus respectivos metodos.
    // Lembre-se de chamar a função config() para configurar o jogo antes de iniciar o menu principal.
    // Exemplo de menu:
    /*==============================================
               MANGA ROSA MEMORY GAME
      ==============================================
      1. INICIAR
      2. PONTUAÇÃO PARTICIPANTES
      3. REGRAS DO JOGO
      4. SAIR
      ==============================================
      INFORME SUA OPÇÃO: 
    */
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
    while (true) { // Loop infinito até que um valor válido seja inserido
        println("Qual o tamanho de tabuleiro que deseja jogar?")
        println("a: 4x4")
        println("b: 6x6")
        println("c: 8x8")  
        println("d: 10x10")
        print("Digite a opção: ")

        val size = readln().trim().lowercase()

        when (size) {
            "a" -> return Pair(4, 4)
            "b" -> return Pair(6, 6)
            "c" -> return Pair(8, 8)
            "d" -> return Pair(10, 10)
            else -> println("Opção inválida! Tente novamente.\n")
        }
    }
}

// Arthur
fun config() {
    println("Qual o apelido do(a) participante 1? ")
    print("Digite o apelido: ")
    nomeJogador1 = readlnOrNull()?.takeIf { it.isNotBlank() } ?: "PARTICIPANTE01"

    while (true) {
        println("Atribua uma cor para ${nomeJogador1}")
        println("a: Vermelho")
        println("b: Azul")
        print("Digite a opção: ")

        corJogador1 = readlnOrNull()?.trim()?.lowercase() ?: "a"

        when (corJogador1) {
            "a" -> {
                corJogador1 = "vermelho"
                break
            }
            "b" -> {
                corJogador1 = "azul"
                break
            }
            else -> println("Opção inválida! Tente novamente.\n")
        }
    }
    println("Cor atribuída ao ${nomeJogador1}: ${corJogador1}")

    println("Qual o apelido do(a) participante 2? ")
    print("Digite o apelido: ")
    nomeJogador2 = readlnOrNull()?.takeIf { it.isNotBlank() } ?: "PARTICIPANTE02"

    while (true) {
        println("Atribua uma cor para ${nomeJogador2}")

        if (corJogador1 == "vermelho") {
            println("b: Azul")
        } else {
            println("a: Vermelho")
        }

        print("Digite a opção: ")
        val inputCorJogador2 = readlnOrNull()?.trim()?.lowercase()

        when (inputCorJogador2) {
            "a" -> {
                if (corJogador1 != "vermelho") {
                    corJogador2 = "vermelho"
                    break
                }
            }
            "b" -> {
                if (corJogador1 != "azul") {
                    corJogador2 = "azul"
                    break
                }
            }
            else -> println("Opção inválida! Tente novamente.\n")
        }
    }
    println("Cor atribuída ao ${nomeJogador2}: ${corJogador2}")

    jogadores[nomeJogador1] = mapOf("cor" to corJogador1, "pontuacao" to 0)
    jogadores[nomeJogador2] = mapOf("cor" to corJogador2, "pontuacao" to 0)
}

// Laysa
fun exibirPontuacao() {
    // Aqui você pode adicionar a lógica para exibir a pontuação dos jogadores.
    // Utilize o map jogadores para acessar os dados dos jogadores.
}

// Matheus
fun exibirRegras() {
    // Aqui você vai dar um print nas regras do jogo. lembando que são 17 regras.
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
    val coresDisponiveis = cores.filter { it != cores[3] }.shuffled()
    val coresMap = mutableMapOf<String, String>()
    val indicePreto = (pares.indices).random()
    
    pares.forEachIndexed { index, par ->
        val cor = if (index == indicePreto) cores[3] else coresDisponiveis[index % coresDisponiveis.size]
        coresMap[par] = cor
    }
    
    return coresMap
}
