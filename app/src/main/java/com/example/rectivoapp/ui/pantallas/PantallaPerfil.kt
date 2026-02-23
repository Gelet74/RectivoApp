package com.example.rectivoapp.ui.pantallas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rectivoapp.ui.navegacion.PantallaConAppBar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

@Composable
fun PantallaPerfil(
    clienteId: Int,
    nombreInicial: String,
    apellido1Inicial: String,
    apellido2Inicial: String,
    dniInicial: String,
    telefonoInicial: String,
    onVolver: () -> Unit = {}
) {
    var nombre by remember { mutableStateOf(nombreInicial) }
    var apellido1 by remember { mutableStateOf(apellido1Inicial) }
    var apellido2 by remember { mutableStateOf(apellido2Inicial) }
    var dni by remember { mutableStateOf(dniInicial) }
    var telefono by remember { mutableStateOf(telefonoInicial) }
    var mensaje by remember { mutableStateOf("") }
    var cargando by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    PantallaConAppBar(titulo = "Mi Perfil", onVolver = onVolver) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF2D2D30))
                .padding(24.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            CampoTexto(label = "Nombre", valor = nombre, onCambio = { nombre = it })
            Spacer(modifier = Modifier.height(12.dp))
            CampoTexto(label = "Apellido 1", valor = apellido1, onCambio = { apellido1 = it })
            Spacer(modifier = Modifier.height(12.dp))
            CampoTexto(label = "Apellido 2", valor = apellido2, onCambio = { apellido2 = it })
            Spacer(modifier = Modifier.height(12.dp))
            CampoTexto(label = "DNI", valor = dni, onCambio = { dni = it })
            Spacer(modifier = Modifier.height(12.dp))
            CampoTexto(label = "Teléfono", valor = telefono, onCambio = { telefono = it })

            if (mensaje.isNotEmpty()) {
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = mensaje,
                    color = if (mensaje.startsWith("Error")) Color.Red else Color.Green,
                    fontSize = 14.sp
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    scope.launch {
                        cargando = true
                        mensaje = ""
                        try {
                            val resultado = withContext(Dispatchers.IO) {
                                val client = OkHttpClient()
                                val jsonObj = JSONObject()
                                jsonObj.put("nombre", nombre.trim())
                                jsonObj.put("apellido1", apellido1.trim())
                                jsonObj.put("apellido2", apellido2.trim())
                                jsonObj.put("dni", dni.trim())
                                jsonObj.put("telefono", telefono.trim())
                                val body = jsonObj.toString()
                                    .toRequestBody("application/json".toMediaType())
                                val request = Request.Builder()
                                    .url("http://192.168.0.25:8080/clientes/$clienteId")
                                    .put(body)
                                    .build()
                                client.newCall(request).execute()
                            }
                            mensaje = if (resultado.isSuccessful) {
                                "Datos actualizados correctamente ✓"
                            } else {
                                "Error al actualizar (${resultado.code})"
                            }
                        } catch (e: Exception) {
                            mensaje = "Error: no se pudo conectar al servidor"
                        } finally {
                            cargando = false
                        }
                    }
                },
                enabled = !cargando,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFF5722)
                )
            ) {
                if (cargando) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(22.dp),
                        color = Color.White,
                        strokeWidth = 2.dp
                    )
                } else {
                    Text(
                        text = "Guardar cambios",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
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
                    text = "Cancelar",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun CampoTexto(label: String, valor: String, onCambio: (String) -> Unit) {
    Text(
        text = label,
        fontSize = 14.sp,
        color = Color(0xFFFF5722),
        fontWeight = FontWeight.Bold
    )
    Spacer(modifier = Modifier.height(4.dp))
    OutlinedTextField(
        value = valor,
        onValueChange = onCambio,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            focusedBorderColor = Color(0xFFFF5722),
            unfocusedBorderColor = Color(0xFFFF5722)
        )
    )
}