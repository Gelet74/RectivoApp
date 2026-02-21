package com.example.rectivoapp.ui.pantallas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rectivoapp.R

@Composable
fun PantallaArmario1Puerta(
    onDerecha: () -> Unit = {},
    onIzquierda: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF2D2D30))
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(40.dp))

        Text(
            text = "Armarios 1 Puerta",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFFF5722)
        )

        Text(
            text = "¿Qué apertura necesitas?",
            fontSize = 16.sp,
            color = Color.White,
            modifier = Modifier.padding(top = 8.dp, bottom = 32.dp)
        )

        TarjetaCategoria(
            titulo = "Apertura Derecha",
            imagenRes = R.drawable.rojo_40_50,
            onClick = onDerecha
        )

        Spacer(modifier = Modifier.height(24.dp))

        TarjetaCategoria(
            titulo = "Apertura Izquierda",
            imagenRes = R.drawable.rojo_40_50,
            onClick = onIzquierda
        )
    }
}