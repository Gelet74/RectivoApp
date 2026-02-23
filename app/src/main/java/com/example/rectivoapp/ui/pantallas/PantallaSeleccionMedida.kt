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
fun PantallaSeleccionMedida(
    puertas: Int,
    onVolver: () -> Unit = {},
    onMedidaSeleccionada: (Int) -> Unit = {}
) {
    PantallaConAppBar(titulo = "Seleccionar Medida", onVolver = onVolver) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF2D2D30))
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "¿Qué medida necesitas?",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            if (puertas == 1) {
                TarjetaCategoria(
                    titulo = "40 cm de ancho",
                    imagenRes = R.drawable.blanco_40_50,
                    onClick = { onMedidaSeleccionada(40) }
                )
                Spacer(modifier = Modifier.height(24.dp))
                TarjetaCategoria(
                    titulo = "50 cm de ancho",
                    imagenRes = R.drawable.blanco_40_50,
                    onClick = { onMedidaSeleccionada(50) }
                )
            } else {
                TarjetaCategoria(
                    titulo = "80 cm de ancho",
                    imagenRes = R.drawable.blanco_80_100,
                    onClick = { onMedidaSeleccionada(80) }
                )
                Spacer(modifier = Modifier.height(24.dp))
                TarjetaCategoria(
                    titulo = "100 cm de ancho",
                    imagenRes = R.drawable.blanco_80_100,
                    onClick = { onMedidaSeleccionada(100) }
                )
            }
        }
    }
}