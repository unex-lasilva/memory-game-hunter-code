package com.card_memory_game.Logic

import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import com.card_memory_game.Model.*


class GameState(val players: List<Player>, val gameSize: Int) {
    var jogoFinalizado by mutableStateOf(false)
    var jogadorVencedor by mutableStateOf<Player?>(null)

    private val totalPairs = (gameSize * gameSize)/2
    private val colors = listOf(Color.Red, Color.Black, Color.Blue, Color.Yellow)
    var cards by mutableStateOf(listOf<MemoryCard>())
    var currentPlayerIndex by mutableStateOf(0)
    private var selectedCards = mutableListOf<MemoryCard>()

    init { resetGame() }


    fun resetGame() {
        val totalPairs = (gameSize * gameSize) / 2
        cards = gerarCartasEmbaralhadas(totalPairs)
        players.forEach { it.score = 0 }
        currentPlayerIndex = 0
        jogoFinalizado = false
        jogadorVencedor = null
    }




    fun onCardClicked(card: MemoryCard): Pair<MemoryCard, MemoryCard>? {
        if (card.isFaceUp || card.isMatched || selectedCards.size >= 2) return null

        card.isFaceUp = true
        selectedCards.add(card)

        if (selectedCards.size == 2) {
            val first = selectedCards[0]
            val second = selectedCards[1]

            val player = players[currentPlayerIndex]
            val adversary = players[(currentPlayerIndex + 1) % players.size]

            val acertouPar = first.value == second.value

            if (acertouPar) {
                first.isMatched = true
                second.isMatched = true
                atualizarPontuacao(player, adversary, first, second, true)
                selectedCards.clear()
                checkGameFinished()
            } else {
                atualizarPontuacao(player, adversary, first, second, false)

                val toReset = Pair(first, second)
                selectedCards.clear()
                nextPlayer()
                return toReset
            }

        }

        return null
    }

private fun atualizarPontuacao(
    player: Player,
    adversary: Player,
    first: MemoryCard,
    second: MemoryCard,
    acertouPar: Boolean
): Int {
    var pontos = 0
    val corPlayer = when (player.color){
        "vermelho" -> "Red"
        "azul" -> "Blue"
        else -> ""

    }
    val corAdversary = when (adversary.color){
        "vermelho" -> "Red"
        "azul" -> "Blue"
        else -> ""

    }

    if (acertouPar) {
        val cor = first.color // ambas têm a mesma cor
        pontos = when {
            cor == "Black" -> 50
            cor == corPlayer -> 5
            cor == corAdversary -> 1
            cor == "Yellow" -> 1
            else -> 0
        }
    } else {
        // só penaliza com base na primeira carta
        val cor = first.color
        pontos = when {
            cor == "Black" -> -50
            cor == corAdversary -> -2
            else -> 0
        }
    }

    player.score += pontos
    if (player.score < 0) player.score = 0
    
    return pontos
}


    fun nextPlayer() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size
    }

    private fun checkGameFinished() {
        val terminou = cards.all { it.isMatched }
        if (terminou) {
            jogoFinalizado = true
            jogadorVencedor = players.maxByOrNull { it.score }
        }
    }

}

fun gerarCartasEmbaralhadas(totalPairs: Int): List<MemoryCard> {
    val naipes = listOf("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L","M", "N", "O","P","Q", "R", "S", "T","U", "V", "W", "X", "Y", "Z")
    val valores = listOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13")

    val usados = mutableSetOf<String>()
    val pares = mutableListOf<MemoryCard>()

    // Distribuição: 1 par preto, metade azul/vermelho, resto amarelo
    val qtdPreto = 1
    val qtdAzul = totalPairs / 4 // 25%
    val qtdVermelho = totalPairs / 4 // 25%
    val qtdAmarelo = totalPairs - qtdPreto - qtdAzul - qtdVermelho // restante

    val corDistribuicao = buildList {
        repeat(qtdPreto) { add("Black") }
        repeat(qtdAzul) { add("Blue") }
        repeat(qtdVermelho) { add("Red") }
        repeat(qtdAmarelo) { add("Yellow") }
    }


    corDistribuicao.forEach { cor ->
        var combinacao: String
        do {
            val valor = valores.random()
            val naipe = naipes.random()
            combinacao = "$valor$naipe"
        } while (combinacao in usados)

        val idBase = pares.size
        val carta1 = MemoryCard(id = idBase, value = combinacao, color = cor)
        val carta2 = MemoryCard(id = idBase + 1, value = combinacao, color = cor)


        pares += listOf(carta1, carta2)
        usados += combinacao
    }

    return pares.shuffled()
}

