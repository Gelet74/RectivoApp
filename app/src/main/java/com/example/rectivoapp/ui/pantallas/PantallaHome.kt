package com.example.rectivoapp.ui.pantallas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PantallaHome(
    onRealizarPedido: () -> Unit = {},
    onMisPedidos: () -> Unit = {},
    onMiPerfil: () -> Unit = {},
    onContacto: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF2D2D30))
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(60.dp))

        Text(
            text = "recTivo",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFFF5722)
        )

        Text(
            text = "¿Qué quieres hacer?",
            fontSize = 16.sp,
            color = Color.White,
            modifier = Modifier.padding(top = 8.dp, bottom = 40.dp)
        )

        BotonMenu(texto = "🛒  Realizar pedido", onClick = onRealizarPedido)
        Spacer(modifier = Modifier.height(16.dp))
        BotonMenu(texto = "📦  Mis pedidos", onClick = onMisPedidos)
        Spacer(modifier = Modifier.height(16.dp))
        BotonMenu(texto = "👤  Mi perfil", onClick = onMiPerfil)
        Spacer(modifier = Modifier.height(16.dp))
        BotonMenu(texto = "📞  Contacto", onClick = onContacto)
    }
}

@Composable
fun BotonMenu(texto: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFFFF5722)
        )
    ) {
        Text(
            text = texto,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
}