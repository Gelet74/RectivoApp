package com.example.rectivoapp.ui.pantallas

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import com.example.rectivoapp.ui.navegacion.PantallaConAppBar
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaResumenPedido(
    seleccion: SeleccionPedido,
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

    PantallaConAppBar(titulo = "Resumen del Pedido", onVolver = onVolver) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF2D2D30))
                .padding(24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = imagenRes),
                    contentDescription = "Imagen del armario",
                    modifier = Modifier
                        .size(120.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .graphicsLayer(scaleX = if (esEspejado) -1f else 1f),
                    contentScale = ContentScale.Fit
                )

                Spacer(modifier = Modifier.width(16.dp))

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "Código del producto",
                        fontSize = 14.sp,
                        color = Color(0xFFFF5722),
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = seleccion.generarCodigo(),
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }

            FilaResumen(
                titulo = "Puertas",
                valor = "${seleccion.puertas} puerta${if (seleccion.puertas == 1) "" else "s"}"
            )

            if (seleccion.puertas == 1) {
                FilaResumen(titulo = "Apertura", valor = nombreApertura)
            }

            FilaResumen(titulo = "Medida", valor = "${seleccion.medida} cm de ancho")
            FilaResumen(titulo = "Color estructura", valor = nombreColor(seleccion.colorEstructura ?: ""))
            FilaResumen(titulo = "Color puerta", valor = nombreColor(seleccion.colorPuerta ?: ""))
            FilaResumen(titulo = "Cantidad", valor = "${seleccion.cantidad} unidad${if (seleccion.cantidad == 1) "" else "es"}")

            Spacer(modifier = Modifier.height(24.dp))

            // Botón para seleccionar fecha de entrega
            val fechaTexto = fechaEntregaSeleccionada
                ?.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                ?: "Seleccionar fecha de entrega"

            OutlinedButton(
                onClick = { mostrarDatePicker = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = Color(0xFFFF5722)
                )
            ) {
                Text(
                    text = fechaTexto,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            // Mensaje de error si la fecha es inválida
            if (mostrarDatePicker.not() && fechaEntregaSeleccionada == null) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Debes seleccionar una fecha mínimo 7 días desde hoy",
                    color = Color.Gray,
                    fontSize = 12.sp
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Botón confirmar — desactivado hasta que haya fecha
            Button(
                onClick = {
                    fechaEntregaSeleccionada?.let {
                        onConfirmar(it.format(DateTimeFormatter.ISO_LOCAL_DATE))
                    }
                },
                enabled = fechaEntregaSeleccionada != null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFF5722),
                    disabledContainerColor = Color(0xFF888888)
                )
            ) {
                Text(
                    text = "Confirmar pedido",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedButton(
                onClick = onVolver,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = Color(0xFFFF5722)
                )
            ) {
                Text(
                    text = "Modificar pedido",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
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
                                .atZone(ZoneId.of("UTC"))
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