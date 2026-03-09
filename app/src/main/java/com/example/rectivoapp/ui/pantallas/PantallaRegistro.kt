package com.example.rectivoapp.ui.pantallas

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rectivoapp.modelo.Cliente
import com.example.rectivoapp.ui.RectivoViewModel
import com.example.rectivoapp.ui.navegacion.PantallaConAppBar

@Composable
fun PantallaRegistro(
    viewModel: RectivoViewModel,
    onRegistroExito: () -> Unit,
    onVolver: () -> Unit
) {
    var nombre by remember { mutableStateOf("") }
    var apellido1 by remember { mutableStateOf("") }
    var apellido2 by remember { mutableStateOf("") }
    var dni by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val errorMsg by viewModel.registroError.collectAsState()
    val cargando by viewModel.registroCargando.collectAsState()

    PantallaConAppBar(titulo = "Crear cuenta", onVolver = onVolver) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF2D2D30))
                .padding(24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            listOf(
                "Nombre" to nombre,
                "Primer apellido" to apellido1,
                "Segundo apellido" to apellido2,
                "DNI" to dni,
                "Teléfono" to telefono,
                "Usuario" to username
            ).forEachIndexed { index, (label, value) ->
                OutlinedTextField(
                    value = value,
                    onValueChange = {
                        when (index) {
                            0 -> nombre = it
                            1 -> apellido1 = it
                            2 -> apellido2 = it
                            3 -> dni = it
                            4 -> telefono = it
                            5 -> username = it
                        }
                    },
                    label = { Text(label, color = Color(0xFFFF5722)) },
                    modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFFFF5722),
                        unfocusedBorderColor = Color.Gray,
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White
                    ),
                    shape = RoundedCornerShape(12.dp)
                )
            }

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Contraseña", color = Color(0xFFFF5722)) },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFFFF5722),
                    unfocusedBorderColor = Color.Gray,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White
                ),
                shape = RoundedCornerShape(12.dp)
            )

            if (errorMsg.isNotEmpty()) {
                Text(
                    text = errorMsg,
                    color = Color.Red,
                    fontSize = 13.sp,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    val cliente = Cliente(
                        id = 0,
                        nombre = nombre,
                        apellido1 = apellido1,
                        apellido2 = apellido2,
                        dni = dni,
                        telefono = telefono,
                        username = username,
                        password = password,
                        numPedido = 0,
                        numFactura = 0
                    )
                    viewModel.registro(cliente, onRegistroExito)
                },
                enabled = !cargando,
                modifier = Modifier.fillMaxWidth().height(55.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF5722))
            ) {
                if (cargando) {
                    CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                } else {
                    Text("Crear cuenta", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White)
                }
            }
        }
    }
}