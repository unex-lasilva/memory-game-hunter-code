package com.card_memory_game


import com.card_memory_game.Model.MemoryCard
import com.card_memory_game.Model.Player
import com.card_memory_game.Logic.GameState

import com.card_memory_game.viewmodel.ConfigViewModel


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
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController


import kotlinx.coroutines.delay

@Composable
fun ScoreScreen(viewModel: ConfigViewModel = viewModel()) {

    val jogadores = viewModel.jogadores
    var showExitDialog by remember { mutableStateOf(false) }

    BackHandler {
        showExitDialog = true
    }

    val players = remember {
        jogadores.map { (nome, info) ->
            Player(nome, info["cor"] as String)
        }
    }
    
    val gameState = remember { GameState(players, 4) }

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


        }


    }

}

@Preview
@Composable
fun ScoreScreenView(){
    ScoreScreen(viewModel = viewModel())
}