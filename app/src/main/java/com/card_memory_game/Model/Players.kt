package com.card_memory_game.Model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class Player(
    val name: String,
    val color: String
) {
    var score by mutableStateOf(0)
}

