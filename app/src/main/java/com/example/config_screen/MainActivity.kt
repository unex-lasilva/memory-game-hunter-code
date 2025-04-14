package com.example.config_screen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.config_screen.ui.theme.Config_screenTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Config_screenTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    TelaConfiguracao(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

enum class CorJogador(val label: String) {
    VERMELHO("Vermelho"),
    AZUL("Azul")
}

enum class TamanhoGrid(val label: String) {
    GRID_4X4("4x4"),
    GRID_6X6("6x6"),
    GRID_8X8("8x8"),
    GRID_10X10("10x10")
}

@Composable
fun TelaConfiguracao(modifier: Modifier = Modifier) {
    var nomeJogador1 by remember { mutableStateOf("") }
    var nomeJogador2 by remember { mutableStateOf("") }

    var corJogador1 by remember { mutableStateOf<CorJogador?>(null) }
    var corJogador2 by remember { mutableStateOf<CorJogador?>(null) }

    var gridSelecionado by remember { mutableStateOf<TamanhoGrid?>(null) }

    val nomeFinal1 = nomeJogador1.ifBlank { "PARTICIPANTE01" }
    val nomeFinal2 = nomeJogador2.ifBlank { "PARTICIPANTE02" }

    val gradientBrush = Brush.verticalGradient(
        colors = listOf(Color(0xFF6A0572), Color(0xFFD90368))
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = gradientBrush)
            .padding(24.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = modifier.fillMaxSize()
        ) {
            Text(
                text = "Configuração dos Jogadores",
                style = MaterialTheme.typography.headlineSmall.copy(fontSize = 22.sp),
                color = Color.White
            )

            Spacer(modifier = Modifier.height(32.dp))

            StyledTextField(
                value = nomeJogador1,
                onValueChange = { nomeJogador1 = it },
                label = "Nome do Jogador 1",
                placeholder = "PARTICIPANTE01"
            )

            ColorSelector(
                selectedColor = corJogador1,
                otherSelectedColor = corJogador2,
                onColorSelected = { corJogador1 = it },
                label = "Cor do Jogador 1"
            )

            Spacer(modifier = Modifier.height(24.dp))

            StyledTextField(
                value = nomeJogador2,
                onValueChange = { nomeJogador2 = it },
                label = "Nome do Jogador 2",
                placeholder = "PARTICIPANTE02"
            )

            ColorSelector(
                selectedColor = corJogador2,
                otherSelectedColor = corJogador1,
                onColorSelected = { corJogador2 = it },
                label = "Cor do Jogador 2"
            )

            Spacer(modifier = Modifier.height(40.dp))

            GridSelector(
                selectedGrid = gridSelecionado,
                onGridSelected = { gridSelecionado = it }
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    println("Jogador 1: $nomeFinal1 (${corJogador1?.label})")
                    println("Jogador 2: $nomeFinal2 (${corJogador2?.label})")
                    println("Grid Selecionado: ${gridSelecionado?.label}")
                },
                enabled = corJogador1 != null && corJogador2 != null && gridSelecionado != null,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF00CFFF),
                    contentColor = Color.White,
                    disabledContainerColor = Color(0xFF5E1B63),
                    disabledContentColor = Color.White.copy(alpha = 0.5f)
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Text(
                    text = "Começar Jogo",
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}

@Composable
fun StyledTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label, color = Color.White) },
        placeholder = { Text(placeholder, color = Color.White.copy(alpha = 0.6f)) },
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White,
            cursorColor = Color.White,
            focusedBorderColor = Color.White,
            unfocusedBorderColor = Color.LightGray,
            focusedLabelColor = Color.White,
            unfocusedLabelColor = Color.White.copy(alpha = 0.6f),
            focusedContainerColor = Color.White.copy(alpha = 0.08f),
            unfocusedContainerColor = Color.White.copy(alpha = 0.06f)
        )
    )
}

@Composable
fun ColorSelector(
    selectedColor: CorJogador?,
    otherSelectedColor: CorJogador?,
    onColorSelected: (CorJogador) -> Unit,
    label: String
) {
    Spacer(modifier = Modifier.height(8.dp))
    Text(text = label, color = Color.White)

    Row(modifier = Modifier.padding(top = 4.dp)) {
        CorJogador.entries.forEach { cor ->
            val desabilitado = cor == otherSelectedColor
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(end = 16.dp)
            ) {
                RadioButton(
                    selected = selectedColor == cor,
                    onClick = { if (!desabilitado) onColorSelected(cor) },
                    enabled = !desabilitado,
                    colors = RadioButtonDefaults.colors(
                        selectedColor = Color.White,
                        unselectedColor = Color.White
                    )
                )
                Text(cor.label, color = if (desabilitado) Color.Gray else Color.White)
            }
        }
    }
}

@Composable
fun GridSelector(
    selectedGrid: TamanhoGrid?,
    onGridSelected: (TamanhoGrid) -> Unit
) {
    Spacer(modifier = Modifier.height(24.dp))
    Text("Tamanho do Grid", color = Color.White)

    Column(modifier = Modifier.padding(top = 8.dp)) {
        TamanhoGrid.entries.chunked(2).forEach { linha ->
            Row(modifier = Modifier.padding(bottom = 8.dp)) {
                linha.forEach { grid ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(end = 16.dp)
                    ) {
                        RadioButton(
                            selected = selectedGrid == grid,
                            onClick = { onGridSelected(grid) },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = Color.White,
                                unselectedColor = Color.White
                            )
                        )
                        Text(text = grid.label, color = Color.White)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TelaConfiguracaoPreview() {
    Config_screenTheme {
        TelaConfiguracao()
    }
}
