package com.card_memory_game

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.Image
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.NavController
import android.app.Activity
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.card_memory_game.viewmodel.ConfigViewModel


val cleanRed = Color(0xFFF25781)
val cleanYellow = Color(0xFFF2BF27)
val cleanPurple = Color(0xFF8E5EBF)
val cleanBlue = Color(0xFF38D0F2)


@Composable
fun MainMenuScreen(coins: Int, navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF3D1D64), Color(0xFFC03C7B))
                )
            )
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.hunter),
                contentDescription = "Logo Hunter",
                modifier = Modifier
                    .width(450.dp)
                    .height(280.dp)
                    .padding(top = 18.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            MenuButton(label="INICIAR", icon=R.drawable.ic_play, color= cleanBlue, action={
                navController.navigate("game")
            })

            MenuButton(label="PONTUAÇÃO", icon=R.drawable.ic_chart, color= cleanPurple, action={
                navController.navigate("score")

            })

            MenuButton(label="REGRAS", icon=R.drawable.ic_info, color= cleanRed, action={
                navController.navigate("rules")

            })

            val context = LocalContext.current
            val activity = context as? Activity

            MenuButton(label="SAIR", icon=R.drawable.ic_exist, color= cleanYellow, action={
                activity?.finish()
            })

            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo rodapé",
                modifier = Modifier
                    .height(800.dp)
                    .padding(top = 5.dp, end = 25.dp)
            )
        }
    }
}

@Composable
fun GameScreen(navController: NavController) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        ConfigScreen(navController = navController)    }
}

@Composable
fun ScoreScreen(navController: NavController) {
    val configViewModel: ConfigViewModel = viewModel()

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        TelaScore(navController = navController,configViewModel)
    }
}

@Composable
fun RulesScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        RegrasTela()
    }
}

@Composable
fun MenuButton(label: String, icon: Int, color: Color, modifier: Modifier = Modifier,action: () -> Unit){
    Button(
        onClick = { action() },
        colors = ButtonDefaults.buttonColors(containerColor = color),
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .padding(8.dp)
            .width(320.dp)
            .height(65.dp),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 8.dp,
            pressedElevation = 12.dp
        ),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = null,
                modifier = Modifier
                    .size(48.dp)
                    .padding(start = 16.dp),
                tint = Color.White
            )

            Spacer(modifier = Modifier.width(20.dp))

            Text(
                label,
                fontSize = 25.sp,
                fontFamily = FontFamily(Font(R.font.poppins_extrabold)),
                color = Color.White
            )
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "mainMenu") {
        composable("mainMenu") { MainMenuScreen(coins = 100, navController = navController) }
        composable("game") { GameScreen(navController = navController) }
        composable("score") {
            ScoreScreen(navController = navController)
        }
        composable("rules") { RulesScreen() }
    }
}

@Preview(showBackground = true)
@Composable
fun MainMenuScreenPreview() {
    MainMenuScreen(coins = 0, navController = rememberNavController())
}