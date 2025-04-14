package com.card_memory_game.Model

sealed class CardType {
    data object Normal : CardType()
    data object Bonus : CardType()
    data object Trap : CardType()
}
