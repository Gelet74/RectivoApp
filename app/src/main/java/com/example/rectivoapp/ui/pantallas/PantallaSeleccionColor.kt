package com.example.rectivoapp.ui.pantallas

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rectivoapp.ui.navegacion.PantallaConAppBar

@Composable
fun PantallaSeleccionColor(
    onVolver: () -> Unit = {},
    onColoresSeleccionados: (String, String) -> Unit = { _, _ -> }
) {
    var colorEstructura by remember { mutableStateOf<String?>(null) }
    var colorPuerta by remember { mutableStateOf<String?>(null) }

    PantallaConAppBar(titulo = "Seleccionar Color", onVolver = onVolver) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF2D2D30))
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            // COLOR ESTRUCTURA
            Text(
                text = "Color de la estructura",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.padding(bottom = 32.dp)
            ) {
                TarjetaColor(
                    codigo = "B",
                    nombre = "Blanco",
                    color = Color.White,
                    seleccionado = colorEstructura == "B",
                    onClick = { colorEstructura = "B" }
                )
                TarjetaColor(
                    codigo = "N",
                    nombre = "Negro",
                    color = Color(0xFF1A1A1A),
                    seleccionado = colorEstructura == "N",
                    onClick = { colorEstructura = "N" }
                )
                TarjetaColor(
                    codigo = "R",
                    nombre = "Rojo",
                    color = Color(0xFFFF5722),
                    seleccionado = colorEstructura == "R",
                    onClick = { colorEstructura = "R" }
                )
            }

            // COLOR PUERTA (solo visible si se ha elegido estructura)
            if (colorEstructura != null) {
                Text(
                    text = "Color de la puerta",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.padding(bottom = 32.dp)
                ) {
                    TarjetaColor(
                        codigo = "B",
                        nombre = "Blanco",
                        color = Color.White,
                        seleccionado = colorPuerta == "B",
                        onClick = { colorPuerta = "B" }
                    )
                    TarjetaColor(
                        codigo = "N",
                        nombre = "Negro",
                        color = Color(0xFF1A1A1A),
                        seleccionado = colorPuerta == "N",
                        onClick = { colorPuerta = "N" }
                    )
                    TarjetaColor(
                        codigo = "R",
                        nombre = "Rojo",
                        color = Color(0xFFFF5722),
                        seleccionado = colorPuerta == "R",
                        onClick = { colorPuerta = "R" }
                    )
                }
            }

            // BOTÓN CONTINUAR
            if (colorEstructura != null && colorPuerta != null) {
                Button(
                    onClick = { onColoresSeleccionados(colorEstructura!!, colorPuerta!!) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFF5722)
                    )
                ) {
                    Text(
                        text = "Continuar",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        }
    }
}

@Composable
fun TarjetaColor(
    codigo: String,
    nombre: String,
    color: Color,
    seleccionado: Boolean,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable { onClick() }
    ) {
        Box(
            modifier = Modifier
                .size(80.dp)
                .background(color, RoundedCornerShape(12.dp))
                .border(
                    width = if (seleccionado) 4.dp else 1.dp,
                    color = if (seleccionado) Color(0xFF4CAF50) else Color.Gray,
                    shape = RoundedCornerShape(12.dp)
                )
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = nombre,
            color = Color.White,
            fontSize = 14.sp,
            fontWeight = if (seleccionado) FontWeight.Bold else FontWeight.Normal
        )
    }
}