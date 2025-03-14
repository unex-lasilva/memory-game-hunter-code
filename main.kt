import kotlin.io.readln;
import kotlin.io.print
import kotlin.random.Random
import corJogador1

val jogadores = mutableMapOf<String, MutableMap<String, Any>>()
var nomeJogador1: String = "PARTICIPANTE01"
var nomeJogador2: String = "PARTICIPANTE02"
var corJogador1: String = ""
var corJogador2: String = ""
val cores = listOf(
    "\u001B[31m", // Vermelho
    "\u001B[34m", // Azul
    "\u001B[33m", // Amarelo
    "\u001B[30m"  // Preto
)
val coresMap = mapOf(
    "\u001B[31m" to "vermelho",
    "\u001B[32m" to "verde",
    "\u001B[33m" to "amarelo",
    "\u001B[34m" to "azul",
    "\u001B[35m" to "roxo",
    "\u001B[36m" to "ciano",
    "\u001B[37m" to "branco",
    "\u001B[30m" to "preto"
)


val reset = "\u001B[0m"

// Matheus
fun main() {
    var opcao: Int = 0
    config()
    while (opcao != 4) {
       
        println("==============================================")
        println(" MANGA ROSA MEMORY GAME")
        println("==============================================")
        println("1. INICIAR")
        println("2. PONTUAÇÃO PARTICIPANTES")
        println("3. REGRAS DO JOGO")
        println("4. SAIR")
        println("==============================================")
        print("INFORME SUA OPÇÃO: ")

    try {
        val opcaoMenu = readln().toInt()
        when (opcaoMenu) {
    
            1 -> {
                println("\n=======================================")
                println(" INICIANDO O JOGO... ")
                println("\n=======================================")                    
                
                iniciarJogo()
            }
            2 -> {
                println("\n=======================================")
                println(" PONTUAÇÃO DOS PARTICIPANTES... ")
                println("\n=======================================")
        
                exibirPontuacao()
            }
            3 -> {
                println("\n==============================")
                println(" REGRAS DO JOGO... ")
                println("\n==============================")

                exibirRegras()
            }
            4 -> {
                println("\n=======================================")
                println(" SAINDO DO JOGO... ATÉ LOGO ")
                println("\n=======================================")
        
                break
            }
            else -> {
                println("\n==========================================")
                println(" OPÇÃO INVÁLIDA! TENTE NOVAMENTE ")
                println("\n==========================================")
            }
        }
    }catch (e: NumberFormatException) {
            println("\n==========================================")
            println(" ENTRADA INVÁLIDA! POR FAVOR, DIGITE UM NÚMERO. ")
            println("\n==========================================")
        }
    }
}


// Henrique
fun iniciarJogo() {
    val (linhas, colunas) = capturarTamanhoTabuleiro()
    val tabuleiro = criarTabuleiro(linhas, colunas)
    val revelados = mutableSetOf<Pair<Int, Int>>()
    val paresFixos = mutableSetOf<Pair<Int, Int>>()
    val paresEncontrados = mutableSetOf<String>()
    var ultimaEscolha: Pair<Int, Int>? = null

    var jogadorAtual = nomeJogador1
    var tentativas = 0

    while (paresEncontrados.size < (linhas * colunas) / 2) {
        try {
            if (tentativas == 3) {
                jogadorAtual = if (jogadorAtual == nomeJogador1) nomeJogador2 else nomeJogador1
                tentativas = 0
            }

            exibirPontuacao()
            exibirTabuleiro(tabuleiro, revelados)

            println("$jogadorAtual, escolha uma linha: ")
            val linha = readln().toInt() - 1
            println("$jogadorAtual, escolha uma coluna: ")
            val coluna = readln().toInt() - 1

            // Verificação de entrada válida
            if (linha !in 0 until linhas || coluna !in 0 until colunas) {
                println("Posição inválida! Escolha dentro do tabuleiro.")
                tentativas = tentativas + 1
                continue
            }
            

            val escolhaAtual = linha to coluna

            // Impedir que o jogador escolha a mesma carta duas vezes seguidas
            if (escolhaAtual == ultimaEscolha) {
                println("Você escolheu a mesma carta! Escolha uma posição diferente.")
                tentativas = tentativas + 1
                continue
            }

            // Impedir que o jogador escolha uma carta já fixada
            if (escolhaAtual in paresFixos) {
                println("Esta carta já foi encontrada! Escolha outra posição.")
                tentativas = tentativas + 1
                continue
            }


            revelados.add(escolhaAtual)
            exibirTabuleiro(tabuleiro, revelados)

            if (ultimaEscolha == null) {
                ultimaEscolha = escolhaAtual
            } else {
                val (linhaAnterior, colunaAnterior) = ultimaEscolha
                val (corAnterior, valorAnterior) = tabuleiro[linhaAnterior][colunaAnterior]
                val (corAtual, valorAtual) = tabuleiro[linha][coluna]
                val corAtualNome = coresMap[corAtual] ?: corAtual  // Se não encontrar, mantém o valor original
                val corAnteriorNome = coresMap[corAnterior] ?: corAnterior  // Se não encontrar, mantém o valor original
                var pontos: Int = 0



                if (valorAtual == valorAnterior) {

                    println("Par encontrado!")
                    paresFixos.add(ultimaEscolha)
                    paresFixos.add(escolhaAtual)
                    val corJogador = jogadores[jogadorAtual]?.get("cor") as String
                    val adversario = if (jogadorAtual == nomeJogador1) nomeJogador2 else nomeJogador1
                    val corAdversario = jogadores[adversario]?.get("cor") as String

                    // Pontuação conforme regras
                    if (corAtual == cores[3]) { // Fundo preto
                        pontos = 50
                    }
                    if (corAtual == cores[1] || corAtual == cores[0]) { // Fundo azul ou vermelho
                        if (corAtualNome == corJogador) { // Fundo da própria cor
                            pontos = 5
                        }
                        pontos = 1
                    }
                    if (corAtualNome == corAdversario) { // Fundo do adversário
                        pontos = -2
                    }
                    if (corAtual == cores[2]) { // Fundo amarelo
                        pontos = 1
                    }
                    
                
                    println("Pontos: $pontos") // DEBUG
                    atualizarPontuacao(jogadorAtual, pontos)
                    paresEncontrados.add(valorAtual)
                    Thread.sleep(1000)

                }else {
                    val corJogador = jogadores[jogadorAtual]?.get("cor") as String
                    val adversario = if (jogadorAtual == nomeJogador1) nomeJogador2 else nomeJogador1
                    val corAdversario = jogadores[adversario]?.get("cor") as String
                    println("Os valores são diferentes! Tente novamente.")

                    if (corAtual == cores[3] || corAnterior == cores[3]) { // Fundo preto
                        pontos = -50
                    }


                    if (corAnteriorNome == corAdversario) { // Fundo do adversário
                        pontos = -2
                    }
                    
                    atualizarPontuacao(jogadorAtual, pontos)
                    Thread.sleep(1000)
                    revelados.removeIf { it !in paresFixos }
                }
                ultimaEscolha = null
                jogadorAtual = if (jogadorAtual == nomeJogador1) nomeJogador2 else nomeJogador1
            }
        } catch (e: Exception) {
            println("Erro na entrada! Digite um número válido para a posição da carta.")
            tentativas = tentativas + 1
        }
    }
    println("Parabéns! Você encontrou todos os pares!")
    if (jogadores[nomeJogador1]?.get("pontuacao") as Int > jogadores[nomeJogador2]?.get("pontuacao") as Int) {
        println("O vencedor é: $nomeJogador1")
    } else {
        println("O vencedor é: $nomeJogador2")
    }
    exibirPontuacao()
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
            else -> println("Por favor, escolha uma das opções de tamanho de tabuleiro disponíveis.\n")
        }
    }
}

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

    jogadores[nomeJogador1] = mutableMapOf("cor" to corJogador1, "pontuacao" to 0)
    jogadores[nomeJogador2] = mutableMapOf("cor" to corJogador2, "pontuacao" to 0)
}

// Laysa
fun exibirPontuacao() {
    jogadores.forEach { (nome, info) ->
        val cor = info["cor"] as String
        val pontuacao = info["pontuacao"] as Int
        println("$nome - Cor: $cor - Pontuação: $pontuacao $reset")
    }
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
    13. Se o participante encontrar uma carta com fundo preto e errar o seu par, perde 50 pontos, mesmo que tenha a pontuação superior à do adversário.
    Mas se acertar, ganha 50 pontos.
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
    val jogadorDados = jogadores[jogador]

    if (jogadorDados != null) {
        val pontuacaoAtual = jogadorDados["pontuacao"] as? Int ?: 0
        val novaPontuacao = maxOf(0, pontuacaoAtual + pontos)

        jogadorDados["pontuacao"] = novaPontuacao

        println("$jogador ganhou $pontos pontos! Pontuação atual: $novaPontuacao\n")
    } else {
        println("Erro: Jogador $jogador não encontrado!")
    }
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
    val total = pares.size
    val redCount = (total * 0.25).toInt()
    val blueCount = (total * 0.25).toInt()
    val blackCount = 1
    val yellowCount = total - redCount - blueCount - blackCount

    val shuffledPares = pares.shuffled()
    val coresMap = mutableMapOf<String, String>()
    var index = 0

    // Atribuir 25% de vermelho (cores[0])
    for (i in 0 until redCount) {
        coresMap[shuffledPares[index]] = cores[0]
        index++
    }
    // Atribuir 25% de azul (cores[1])
    for (i in 0 until blueCount) {
        coresMap[shuffledPares[index]] = cores[1]
        index++
    }
    // Atribuir 1 cor preta (cores[3])
    if (index < total) {
        coresMap[shuffledPares[index]] = cores[3]
        index++
    }
    // Atribuir o restante com amarelo (cores[2])
    while (index < total) {
        coresMap[shuffledPares[index]] = cores[2]
        index++
    }

    return coresMap
}

