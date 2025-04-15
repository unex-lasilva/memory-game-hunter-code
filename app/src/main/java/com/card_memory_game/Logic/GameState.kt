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

            var points = 0

            if (first.value == second.value) {
                first.isMatched = true
                second.isMatched = true

                // Pontuação baseada nas cores
                points = when {
                    first.color == "Black" -> 50
                    first.color == player.color -> 5
                    first.color == adversary.color -> 1
                    first.color == "Yellow" -> 1
                    else -> 1
                }

                player.score += points
                selectedCards.clear()
                checkGameFinished()

            } else {
                // Penalidade
                if (first.color == "Black") {
                    points = -50
                } else if (first.color == adversary.color) {
                    points = -2
                }



                player.score += points

                if (player.score < 0 ){
                    player.score = 0
                }

                val toReset = Pair(first, second)
                selectedCards.clear()
                nextPlayer()
                return toReset

            }

        }

        return null
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

