package com.example.rectivoapp.ui.pantallas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rectivoapp.ui.navegacion.PantallaConAppBar

@Composable
fun PantallaContacto(
    onVolver: () -> Unit = {}
) {
    PantallaConAppBar(titulo = "Contacto", onVolver = onVolver) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF2D2D30))
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "recTivo",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFFF5722)
            )

            Text(
                text = "Tu Tienda de Armarios",
                fontSize = 14.sp,
                color = Color.White,
                modifier = Modifier.padding(top = 4.dp, bottom = 32.dp)
            )

            TarjetaContacto(emoji = "📍", titulo = "Dirección", valor = "Carrer Enric Valor, s/n, 46980 Paterna, Valencia")
            Spacer(modifier = Modifier.height(16.dp))
            TarjetaContacto(emoji = "📞", titulo = "Teléfono", valor = "+34  961 20 62 80")
            Spacer(modifier = Modifier.height(16.dp))
            TarjetaContacto(emoji = "✉️", titulo = "Email", valor = "info@rectivo.com")
            Spacer(modifier = Modifier.height(16.dp))
            TarjetaContacto(emoji = "🕐", titulo = "Horario", valor = "Lun - Vie: 9:00 - 20:00")
        }
    }
}

@Composable
fun TarjetaContacto(emoji: String, titulo: String, valor: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF3D3D40)),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = emoji, fontSize = 28.sp)
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = titulo,
                    fontSize = 12.sp,
                    color = Color(0xFFFF5722),
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = valor,
                    fontSize = 15.sp,
                    color = Color.White
                )
            }
        }
    }
}