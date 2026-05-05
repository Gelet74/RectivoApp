package com.example.rectivoapp.ui.pantallas

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.draw.scale
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rectivoapp.R
import com.example.rectivoapp.modelo.SeleccionPedido
import com.example.rectivoapp.ui.RectivoViewModel
import com.example.rectivoapp.ui.navegacion.PantallaConAppBar
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaResumenPedido(
    seleccion: SeleccionPedido,
    carrito: List<RectivoViewModel.LineaCarrito> = emptyList(),
    viewModel: RectivoViewModel,
    onVolver: () -> Unit = {},
    onConfirmar: (String) -> Unit = {}
) {
    var mostrarDatePicker by remember { mutableStateOf(false) }
    var fechaEntregaSeleccionada by remember { mutableStateOf<LocalDate?>(null) }
    val fechaMinima = LocalDate.now().plusDays(7)

    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = fechaMinima
            .atStartOfDay(ZoneId.of("UTC"))
            .toInstant()
            .toEpochMilli()
    )

    val nombreColor = { codigo: String ->
        when (codigo) {
            "B" -> "Blanco"
            "N" -> "Negro"
            "R" -> "Rojo"
            else -> codigo
        }
    }

    val nombreApertura = when (seleccion.apertura) {
        "derecha" -> "Derecha"
        "izquierda" -> "Izquierda"
        else -> "-"
    }

    val esEspejado = seleccion.puertas == 1 && seleccion.apertura == "derecha"

    android.util.Log.d("IMAGEN", "puertas=${seleccion.puertas} colorE=${seleccion.colorEstructura} colorP=${seleccion.colorPuerta}")

    val imagenRes = when {
        seleccion.colorEstructura == "B" && seleccion.colorPuerta == "B" && seleccion.puertas == 2 -> R.drawable.blanco_80_100
        seleccion.colorEstructura == "B" && seleccion.colorPuerta == "N" && seleccion.puertas == 2 -> R.drawable.blanco_negro_80_100
        seleccion.colorEstructura == "B" && seleccion.colorPuerta == "R" && seleccion.puertas == 2 -> R.drawable.blanco_rojo_80_100
        seleccion.colorEstructura == "N" && seleccion.colorPuerta == "N" && seleccion.puertas == 2 -> R.drawable.negro_80_100
        seleccion.colorEstructura == "N" && seleccion.colorPuerta == "B" && seleccion.puertas == 2 -> R.drawable.negro_blanco_80_100
        seleccion.colorEstructura == "N" && seleccion.colorPuerta == "R" && seleccion.puertas == 2 -> R.drawable.negro_rojo_80_100
        seleccion.colorEstructura == "R" && seleccion.colorPuerta == "R" && seleccion.puertas == 2 -> R.drawable.rojo_80_100
        seleccion.colorEstructura == "R" && seleccion.colorPuerta == "B" && seleccion.puertas == 2 -> R.drawable.rojo_blanco_80_100
        seleccion.colorEstructura == "R" && seleccion.colorPuerta == "N" && seleccion.puertas == 2 -> R.drawable.rojo_negro_80_100
        seleccion.colorEstructura == "B" && seleccion.colorPuerta == "B" -> R.drawable.blanco_40_50
        seleccion.colorEstructura == "B" && seleccion.colorPuerta == "N" -> R.drawable.blanco_negro_40_50
        seleccion.colorEstructura == "B" && seleccion.colorPuerta == "R" -> R.drawable.blanco_rojo_40_50
        seleccion.colorEstructura == "N" && seleccion.colorPuerta == "N" -> R.drawable.negro_40_50
        seleccion.colorEstructura == "N" && seleccion.colorPuerta == "B" -> R.drawable.negro_blanco_40_50
        seleccion.colorEstructura == "N" && seleccion.colorPuerta == "R" -> R.drawable.negro_rojo_40_50
        seleccion.colorEstructura == "R" && seleccion.colorPuerta == "R" -> R.drawable.rojo_40_50
        seleccion.colorEstructura == "R" && seleccion.colorPuerta == "B" -> R.drawable.rojo_blanco_40_50
        seleccion.colorEstructura == "R" && seleccion.colorPuerta == "N" -> R.drawable.rojo_negro_40_50
        else -> R.drawable.blanco_40_50
    }

    PantallaConAppBar(titulo = "Resumen del Pedido", onVolver = onVolver) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF2D2D30))
                .padding(paddingValues)
        ) {

            // ── Cabecera: imagen + código ──
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .clip(RoundedCornerShape(bottomStart = 12.dp, bottomEnd = 12.dp))
                    .background(Color(0xFF2D2D30)),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = imagenRes),
                    contentDescription = "Imagen del armario",
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 260.dp)
                        .graphicsLayer {
                            scaleX = if (esEspejado) -1f else 1f
                        },
                    contentScale = ContentScale.Fit,
                    alignment = Alignment.Center
                )

                Column(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(12.dp)
                        .background(Color(0xCC000000), RoundedCornerShape(8.dp))
                        .padding(horizontal = 10.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = "Código del producto",
                        fontSize = 10.sp,
                        color = Color(0xFFFF5722),
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = seleccion.generarCodigo(),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White
                    )
                }
            }

            // ── Filas de detalle ──
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)) {

                FilaDetalle("Puertas", "${seleccion.puertas} puerta${if (seleccion.puertas == 1) "" else "s"}")
                if (seleccion.puertas == 1) FilaDetalle("Apertura", nombreApertura)
                FilaDetalle("Medida", "${seleccion.medida} cm de ancho")
                FilaDetalle("Color estructura", nombreColor(seleccion.colorEstructura ?: ""))
                FilaDetalle("Color puerta", nombreColor(seleccion.colorPuerta ?: ""))
                FilaDetalle("Cantidad", "${seleccion.cantidad} unidad${if (seleccion.cantidad == 1) "" else "es"}")

                // ── Precios ──
                if (seleccion.precioUnitario > 0.0) {
                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 8.dp),
                        color = Color(0xFF555558)
                    )
                    FilaDetalle(
                        "Precio unitario",
                        "${"%.2f".format(seleccion.precioUnitario)} €",
                        valorColor = Color.White
                    )

                    val totalLinea = seleccion.precioUnitario * seleccion.cantidad
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .background(Color(0xFFFF5722), RoundedCornerShape(10.dp))
                            .padding(horizontal = 14.dp, vertical = 10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Total este pedido", fontSize = 14.sp, color = Color.White, fontWeight = FontWeight.Bold)
                        Text("${"%.2f".format(totalLinea)} €", fontSize = 20.sp, color = Color.White, fontWeight = FontWeight.ExtraBold)
                    }
                }

                // ── Sumatorio del carrito ──
                if (carrito.isNotEmpty()) {
                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 8.dp),
                        color = Color(0xFF555558)
                    )
                    Text(
                        text = "Pedidos anteriores",
                        fontSize = 11.sp,
                        color = Color(0xFFAAAAAA),
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                    carrito.forEachIndexed { index, linea ->
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(vertical = 2.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("#${index + 1}  ${linea.seleccion.generarCodigo()}  ×${linea.seleccion.cantidad}", fontSize = 12.sp, color = Color(0xFFCCCCCC))
                            Text("${"%.2f".format(linea.subtotal)} €", fontSize = 12.sp, color = Color(0xFFCCCCCC))
                        }
                    }
                    HorizontalDivider(modifier = Modifier.padding(vertical = 6.dp), color = Color(0xFF555558))
                    val totalAcumulado = carrito.sumOf { it.subtotal } +
                            (if (seleccion.precioUnitario > 0.0) seleccion.precioUnitario * seleccion.cantidad else 0.0)
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 2.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("TOTAL ACUMULADO", fontSize = 14.sp, color = Color(0xFFFF5722), fontWeight = FontWeight.ExtraBold)
                        Text("${"%.2f".format(totalAcumulado)} €", fontSize = 22.sp, color = Color(0xFFFF5722), fontWeight = FontWeight.ExtraBold)
                    }
                }
            }

            // ── Fecha + botones ──
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)) {

                val fechaTexto = fechaEntregaSeleccionada
                    ?.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                    ?: "📅  Seleccionar fecha de entrega"

                OutlinedButton(
                    onClick = { mostrarDatePicker = true },
                    modifier = Modifier.fillMaxWidth().height(48.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFFFF5722))
                ) {
                    Text(fechaTexto, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                }

                if (fechaEntregaSeleccionada == null) {
                    Text(
                        text = "Mínimo 7 días desde hoy",
                        color = Color.Gray,
                        fontSize = 11.sp,
                        modifier = Modifier.padding(top = 2.dp, start = 4.dp)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = {
                        fechaEntregaSeleccionada?.let {
                            onConfirmar(it.format(DateTimeFormatter.ISO_LOCAL_DATE))
                        }
                    },
                    enabled = fechaEntregaSeleccionada != null,
                    modifier = Modifier.fillMaxWidth().height(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFF5722),
                        disabledContainerColor = Color(0xFF888888)
                    )
                ) {
                    Text("Confirmar pedido", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White)
                }

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedButton(
                    onClick = onVolver,
                    modifier = Modifier.fillMaxWidth().height(44.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFFFF5722))
                ) {
                    Text("Modificar pedido", fontSize = 14.sp, fontWeight = FontWeight.Bold)
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }

    // DatePickerDialog
    if (mostrarDatePicker) {
        DatePickerDialog(
            onDismissRequest = { mostrarDatePicker = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        val millis = datePickerState.selectedDateMillis
                        if (millis != null) {
                            val fecha = Instant.ofEpochMilli(millis)
                                .atZone(ZoneId.of("Europe/Madrid"))
                                .toLocalDate()
                            if (!fecha.isBefore(fechaMinima)) {
                                fechaEntregaSeleccionada = fecha
                                mostrarDatePicker = false
                            }
                        }
                    }
                ) {
                    Text("Aceptar", color = Color(0xFFFF5722))
                }
            },
            dismissButton = {
                TextButton(onClick = { mostrarDatePicker = false }) {
                    Text("Cancelar", color = Color(0xFFFF5722))
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}

@Composable
fun FilaDetalle(titulo: String, valor: String, valorColor: Color = Color.White) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 7.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = titulo, fontSize = 13.sp, color = Color(0xFFFF5722), fontWeight = FontWeight.Bold)
        Text(text = valor, fontSize = 14.sp, color = valorColor, fontWeight = FontWeight.Medium)
    }
    HorizontalDivider(color = Color(0xFF484848), thickness = 0.5.dp)
}

@Composable
fun FilaResumen(titulo: String, valor: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF3D3D40))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = titulo,
                fontSize = 14.sp,
                color = Color(0xFFFF5722),
                fontWeight = FontWeight.Bold
            )
            Text(
                text = valor,
                fontSize = 15.sp,
                color = Color.White,
                fontWeight = FontWeight.Medium
            )
        }
    }
}