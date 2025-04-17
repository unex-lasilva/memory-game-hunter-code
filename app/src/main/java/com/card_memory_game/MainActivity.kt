package com.card_memory_game

import com.card_memory_game.Model.MemoryCard
import com.card_memory_game.Model.Player
import com.card_memory_game.Logic.GameState

import com.card_memory_game.viewmodel.ConfigViewModel
import com.card_memory_game.ConfigScreen


import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.activity.compose.BackHandler
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController


import kotlinx.coroutines.delay

@Composable
fun MemoryGameScreen(viewModel: ConfigViewModel = viewModel()) {

    val jogadores = viewModel.jogadores
    val gridSize = viewModel.gridSize
    var showExitDialog by remember { mutableStateOf(false) }
    BackHandler {
        showExitDialog = true
    }
    val navController = rememberNavController()

//    NavHost(navController = navController, startDestination = "game") {
//        composable("mainMenu") { MainMenuScreen(coins = 100, navController = navController) }
//        composable("game") { MemoryGameScreen()}
//    }




    val players = remember {
        jogadores.map { (nome, info) ->
            Player(nome, info["cor"] as String)
        }
    }

    val gameState = remember { GameState(players, gridSize) }

    var toReset by remember { mutableStateOf<Pair<MemoryCard, MemoryCard>?>(null) }



    // Se houver cartas para virar de volta, aguarde e depois vire
    LaunchedEffect(toReset) {
        toReset?.let { pair ->
            delay(1000)
            pair.first.isFaceUp = false
            pair.second.isFaceUp = false
            toReset = null
        }
    }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF3D1D64), Color(0xFFC03C7B))
                )
            )
            .padding(top = 30.dp),
        color = Color.Transparent

    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(30.dp))
                    .background(Color.White.copy(alpha = 0.3f))

            ) {
                Text(
                    text = "Jogador atual:" + " ${gameState.players[gameState.currentPlayerIndex].name}",
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(15.dp)
                        .clip(RoundedCornerShape(20.dp)))
            }

            Spacer(modifier = Modifier.height(50.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
                players.forEach {
                    Column(horizontalAlignment = Alignment.CenterHorizontally){
                        Box(
                            modifier = Modifier
                                .size(90.dp)
                                .clip(RoundedCornerShape(50.dp))
                                .padding(10.dp)
                                .background(
                                    color = when (it.color) {
                                        "vermelho" -> Color.Red
                                        "azul" -> Color.Blue
                                        else -> Color.Gray
                                    }
                                ),
                            Alignment.Center
                        ){
                            Text(
                                text = "${it.score}",
                                fontSize = 35.sp,
                                textAlign = TextAlign.Center,
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                style = TextStyle(
                                    shadow = Shadow(
                                        color = Color.Black.copy(alpha =  0.7f),
                                        offset = Offset(3f,2f),
                                        blurRadius = 5f
                                    )
                                )
                            )
                        }

                        Text(
                            modifier = Modifier.padding(top = 12.dp),
                            text = it.name,
                            fontSize = 20.sp,
                            textAlign = TextAlign.Center,
                            color = Color.White,
                            fontWeight = FontWeight.W600,
                            style = TextStyle(
                                shadow = Shadow(
                                    color = Color.Black.copy(alpha =  0.7f),
                                    offset = Offset(3f,2f),
                                    blurRadius = 5f
                                )
                            ))
                    }


                }
            }

            Spacer(modifier = Modifier.height(100.dp))

            Column {
                for (i in 0 until gameState.cards.size step gridSize) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                        for (j in 0 until gridSize) {
                            if (i + j < gameState.cards.size) {
                                val card = gameState.cards[i + j]
                                MemoryCardView(card, gridSize) {
                                    val reset = gameState.onCardClicked(card)
                                    if (reset != null) {
                                        toReset = reset
                                    }
                                }
                            }
                        }
                    }
                }
                if (gameState.jogoFinalizado) {
                    AlertDialog(
                        onDismissRequest = { /* Do nothing ou algo opcional */ },
                        title = {
                            Text("Fim de Jogo")
                        },
                        text = {
                            Text("O vencedor é: ${gameState.jogadorVencedor?.name} com ${gameState.jogadorVencedor?.score} pontos!")
                        },
                        confirmButton = {
                            Button(onClick = {
                                gameState.resetGame()
                            }) {
                                Text("Jogar novamente")
                            }
                        },
                        dismissButton = {
                            Button(onClick = {
                                
                                // Fechar o app ou voltar à tela inicial
                            }) {
                                Text("Sair")
                            }
                        }
                    )
                }


                if (showExitDialog) {

                    AlertDialog(
                        onDismissRequest = { showExitDialog = false },
                        title = { Text("Sair do jogo") },
                        text = { Text("Você tem certeza que deseja sair do jogo?") },
                        confirmButton = {
                            Button(onClick = {

                            }) {
                                Text("Sim")
                            }

                        },
                        dismissButton = {
                            Button(onClick = {
                                showExitDialog = false
                            }) {
                                Text("Não")
                            }
                        }
                    )
                }



            }
        }


    }

}



@Composable
fun MemoryCardView(card: MemoryCard, size: Int, onClick: () -> Unit){
    val rotation by animateFloatAsState(if (card.isFaceUp) 0f else 180f)
    val alpha by animateFloatAsState(if (card.isMatched) 0.3f else 1f)
    val corCarta = when (card.color) {
        "Blue" -> Color.Blue
        "Red" -> Color.Red
        "Yellow" ->  Color(0xFFF2BF27)
        else -> Color.Gray
    }


    Box(
        modifier = Modifier
            .padding(
                when (size) {
                    8 -> 2.dp
                    10 -> 2.dp
                    else -> 4.dp
                }
            )
            .clickable { onClick() }
            .size(
                when (size) {
                    4 -> 60.dp
                    6 -> 50.dp
                    8 -> 40.dp
                    10 -> 30.dp
                    else -> 30.dp
                }
            )
            .rotate(rotation)
            .alpha(alpha)
            .background(
                color = if (card.isFaceUp) corCarta else Color.White,
                shape = RoundedCornerShape(10.dp)
            ),
        contentAlignment = Alignment.Center
    )  {
        if (card.isFaceUp || card.isMatched) {
            Text(
                text = card.value,
                color = Color.White,
                style = MaterialTheme.typography.titleMedium
            )
        } else {
            val naipes = listOf("♠️","♣️","♥️","♦️")

            // Aqui você pode mostrar um símbolo de verso, tipo "?"
            Text(text = naipes.random(), style = MaterialTheme.typography.titleMedium, modifier = Modifier.rotate(rotation))
        }
    }

}



@Preview
@Composable
fun MemoryCardView(){
    MemoryGameScreen(viewModel = viewModel())
}