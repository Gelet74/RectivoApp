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
fun PantallaSeleccionPuertas(
    onVolver: () -> Unit = {},
    on1Puerta: () -> Unit = {},
    on2Puertas: () -> Unit = {}
) {
    PantallaConAppBar(titulo = "Realizar Pedido", onVolver = onVolver) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF2D2D30))
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "¿De cuántas puertas lo quieres?",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(bottom = 16.dp, top = 50.dp)
            )

            TarjetaCategoria(
                titulo = "1 Puerta",
                imagenRes = R.drawable.rojo_40_50,
                onClick = on1Puerta
            )

            Spacer(modifier = Modifier.height(24.dp))

            TarjetaCategoria(
                titulo = "2 Puertas",
                imagenRes = R.drawable.negro_80_100,
                onClick = on2Puertas
            )
        }
    }
}