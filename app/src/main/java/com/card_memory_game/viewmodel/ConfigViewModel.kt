package com.card_memory_game.viewmodel

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel

class ConfigViewModel : ViewModel() {

    private val _jogadores = mutableStateMapOf<String, MutableMap<String, Any>>()
    val jogadores: Map<String, MutableMap<String, Any>> get() = _jogadores

    private var _gridSize by mutableIntStateOf(0)
    val gridSize: Int get() = _gridSize

    fun salvarJogadores(nome1: String, cor1: String, nome2: String, cor2: String) {
        _jogadores.clear()
        _jogadores[nome1] = mutableMapOf("cor" to cor1, "pontuacao" to 0)
        _jogadores[nome2] = mutableMapOf("cor" to cor2, "pontuacao" to 0)
    }

    fun salvarTamanhoGrid(grid: Int) {
        _gridSize = grid
    }
}
