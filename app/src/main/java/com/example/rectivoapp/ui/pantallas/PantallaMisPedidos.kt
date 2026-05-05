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
import com.example.rectivoapp.modelo.PedidoBD
import com.example.rectivoapp.ui.navegacion.PantallaConAppBar

@Composable
fun PantallaMisPedidos(
    pedidos: List<PedidoBD>,
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
                        .padding(horizontal = 16.dp, vertical = 10.dp)
                        .padding(top = 50.dp),
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
                            color = Color.Gray,
                            fontSize = 16.sp
                        )
                    }
                }
                else -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(pedidos) { pedido ->
                            TarjetaPedidoBD(pedido = pedido)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TarjetaPedidoBD(pedido: PedidoBD) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF3D3D40)),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            // ── Cabecera: nº pedido + fecha ──
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Pedido #${pedido.id}",
                    fontWeight = FontWeight.ExtraBold,
                    color = Color(0xFFFF5722),
                    fontSize = 16.sp
                )
                Text(
                    text = pedido.fecha,
                    color = Color(0xFFAAAAAA),
                    fontSize = 12.sp
                )
            }

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 10.dp),
                color = Color(0xFF555558)
            )

            // ── Código del producto ──
            if (pedido.productoCodigo.isNotEmpty()) {
                FilaPedido("Código", pedido.productoCodigo)
            }

            // ── Descripción ──
            if (pedido.productoDescripcion.isNotEmpty()) {
                FilaPedido("Descripción", pedido.productoDescripcion)
            }

            // ── Cantidad ──
            if (pedido.cantidad > 0) {
                FilaPedido(
                    "Cantidad",
                    "${pedido.cantidad} unidad${if (pedido.cantidad == 1) "" else "es"}"
                )
            }

            // ── Precio unitario ──
            if (pedido.precioUnitario > 0.0) {
                FilaPedido("Precio unitario", "${"%.2f".format(pedido.precioUnitario)} €")
            }

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 8.dp),
                color = Color(0xFF555558)
            )

            // ── Total ──
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFFF5722), RoundedCornerShape(10.dp))
                    .padding(horizontal = 14.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Total",
                    fontSize = 14.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "${"%.2f".format(pedido.precioTotal)} €",
                    fontSize = 18.sp,
                    color = Color.White,
                    fontWeight = FontWeight.ExtraBold
                )
            }
        }
    }
}

@Composable
private fun FilaPedido(etiqueta: String, valor: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = etiqueta,
            fontSize = 13.sp,
            color = Color(0xFFFF5722),
            fontWeight = FontWeight.Bold
        )
        Text(
            text = valor,
            fontSize = 13.sp,
            color = Color.White,
            fontWeight = FontWeight.Medium
        )
    }
}