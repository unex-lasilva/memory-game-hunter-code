package com.example.regras

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.regras.ui.theme.RegrasTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RegrasTheme {
                RegrasTela()
            }
        }
    }
}

@Composable
fun RegrasTela() {
    val scrollState = rememberScrollState()
    val gradient = Brush.verticalGradient(
        colors = listOf(Color(0xFF4A148C), Color(0xFFAD1457)) // Roxo escuro → Rosa escuro
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(gradient)
            .padding(horizontal = 24.dp, vertical = 16.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TituloSecao("Regras Gerais")
        Regra("1. No início do jogo, o participante deve escolher o tamanho do tabuleiro: 4x4, 6x6, 8x8 ou 10x10.")
        Regra("2. Todos os pares de figuras possuem uma cor de fundo: vermelho, azul, amarelo ou preto.")
        Regra("3. Em todo jogo deve existir uma figura de fundo preto.")
        Regra("4. Em todo jogo, metade das figuras devem ter fundo azul e vermelho.")
        Regra("5. As demais figuras que sobram no jogo devem ter fundo amarelo.")
        Regra("6. Cada participante deve ter atribuído a si uma cor (vermelho ou azul) no início do jogo.")
        Regra("7. Todo participante deve ter um nome registrado. Caso não tenha, será atribuído: 'PARTICIPANTE01' e 'PARTICIPANTE02'.")
        Regra("8. Cada participante possui uma pontuação atrelada a si.")
        Regra("9. Se encontrar um par com fundo amarelo, ganha 1 ponto.")
        Regra("10. Se encontrar um par com a sua cor, ganha 5 pontos.")
        Regra("11. Se encontrar um par com a cor do adversário e errar, perde 2 pontos. Se acertar, ganha 1 ponto.")
        Regra("12. Pontuação mínima é 0. Não pode ser negativa.")
        Regra("13. Se errar um par com fundo preto, perde 50 pontos. Se acertar, ganha 50 pontos.")

        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
fun TituloSecao(text: String) {
    // Cores de base (iguais ao fundo)
    val corRoxo = Color(0xFF4A148C)
    val corRosa = Color(0xFFAD1457)

    // Define o gradiente com base no título
    val linhaGradiente = when {
        text.contains("Regras Gerais", ignoreCase = true) -> Brush.horizontalGradient(
            colors = listOf(corRoxo.copy(alpha = 0.4f), corRosa.copy(alpha = 0.4f))
        )
        text.contains("Interação", ignoreCase = true) -> Brush.horizontalGradient(
            colors = listOf(corRoxo.copy(alpha = 0.8f), corRosa.copy(alpha = 0.8f))
        )
        else -> Brush.horizontalGradient(
            colors = listOf(corRoxo.copy(alpha = 0.6f), corRosa.copy(alpha = 0.6f))
        )
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 28.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = text.uppercase(),
            fontSize = 34.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Color.White,
            textAlign = TextAlign.Center,
            lineHeight = 50.sp,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(10.dp))

        Box(
            modifier = Modifier
                .height(3.dp)
                .width(180.dp)
                .background(linhaGradiente)
        )
    }
}





@Composable
fun Regra(texto: String) {
    val partes = texto.split(".", limit = 2)
    val numero = partes.getOrNull(0)?.trim() ?: ""
    val regra = partes.getOrNull(1)?.trim() ?: texto

    Text(
        text = buildAnnotatedString {
            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                append("$numero. ")
            }
            withStyle(style = SpanStyle(fontWeight = FontWeight.Normal)) {
                append(regra)
            }
        },
        fontSize = 22.sp,
        color = Color.White,
        textAlign = TextAlign.Center, // ← centralizado, como você quer
        lineHeight = 32.sp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp)
    )
}
