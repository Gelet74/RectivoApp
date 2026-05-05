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
    modoOffline: Boolean = false,
    onVolver: () -> Unit = {}
) {
    PantallaConAppBar(titulo = "Mis Pedidos", onVolver = onVolver) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF2D2D30))
        ) {
            // ── Banner sin conexión ──
            if (modoOffline) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFF5C3317))
                        .padding(horizontal = 16.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "📵  Sin conexión — mostrando datos guardados",
                        color = Color(0xFFFFCC80),
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            // ── Contenido ──
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                when {
                    cargando -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(color = Color(0xFFFF5722))
                        }
                    }
                    pedidos.isEmpty() -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = if (modoOffline)
                                    "No hay pedidos guardados localmente."
                                else
                                    "No tienes pedidos todavía.",
                                color = Color.White,
                                fontSize = 16.sp
                            )
                        }
                    }
                    else -> {
                        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                            items(pedidos) { pedido ->
                                TarjetaPedido(pedido = pedido)
                            }
                        }
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
                        "Pendiente"    -> Color(0xFFFFB300)
                        "EnCurso"      -> Color(0xFF29B6F6)
                        "Cerrada"      -> Color(0xFF66BB6A)
                        "Sin conexión" -> Color(0xFFAAAAAA)
                        else           -> Color.White
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