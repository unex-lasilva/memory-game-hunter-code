package com.card_memory_game.Model

import androidx.compose.runtime.*

class MemoryCard(
    val id: Int,
    val color: String,
    val type: CardType = CardType.Normal,
    val value: String, // Ex: "A2", "9D"
    isFaceUp: Boolean = false,
    isMatched: Boolean = false
) {
    var isFaceUp by mutableStateOf(isFaceUp)
    var isMatched by mutableStateOf(isMatched)
}
