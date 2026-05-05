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
import com.example.rectivoapp.ui.navegacion.PantallaConAppBar
import com.example.rectivoapp.ui.navegacion.TarjetaCategoria

@Composable
fun PantallaArmario1Puerta(
    onVolver: () -> Unit = {},
    onDerecha: () -> Unit = {},
    onIzquierda: () -> Unit = {}
) {
    PantallaConAppBar(titulo = "Armario 1 Puerta", onVolver = onVolver) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF2D2D30))
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "¿Qué apertura necesitas?",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(bottom = 24.dp, top = 50.dp)
            )

            TarjetaCategoria(
                titulo = "Apertura Derecha",
                imagenRes = R.drawable.negro_40_50,
                espejado = true,
                onClick = onDerecha
            )

            Spacer(modifier = Modifier.height(24.dp))

            TarjetaCategoria(
                titulo = "Apertura Izquierda",
                imagenRes = R.drawable.negro_40_50,
                onClick = onIzquierda
            )
        }
    }
}