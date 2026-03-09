package com.example.rectivoapp.ui.pantallas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rectivoapp.modelo.Pedido
import com.example.rectivoapp.ui.navegacion.PantallaConAppBar

@Composable
fun PantallaMisPedidos(
    pedidos: List<Pedido>,
    cargando: Boolean,
    onVolver: () -> Unit = {}
) {
    PantallaConAppBar(titulo = "Mis Pedidos", onVolver = onVolver) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF2D2D30))
                .padding(16.dp)
        ) {
            if (cargando) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = Color(0xFFFF5722))
                }
            } else if (pedidos.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        text = "No tienes pedidos todavía.",
                        color = Color.White,
                        fontSize = 16.sp
                    )
                }
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    items(pedidos) { pedido ->
                        TarjetaPedido(pedido = pedido)
                    }
                }
            }
        }
    }
}

@Composable
fun TarjetaPedido(pedido: Pedido) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF3D3D40)),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Pedido #${pedido.id}",
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFFF5722),
                    fontSize = 16.sp
                )
                Text(
                    text = pedido.estado,
                    color = when (pedido.estado) {
                        "Pendiente" -> Color(0xFFFFB300)
                        "EnCurso" -> Color(0xFF29B6F6)
                        "Cerrada" -> Color(0xFF66BB6A)
                        else -> Color.White
                    },
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Fecha: ${pedido.fechaPedido}", color = Color.White, fontSize = 14.sp)
            pedido.fechaEntrega?.let {
                Text(text = "Entrega: $it", color = Color.White, fontSize = 14.sp)
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Total: ${"%.2f".format(pedido.total)} €",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp
            )
        }
    }
}