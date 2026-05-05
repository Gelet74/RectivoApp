package com.example.rectivoapp.ui.pantallas

import com.example.rectivoapp.R
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rectivoapp.ui.RectivoViewModel

@Composable
fun Login(
    viewModel: RectivoViewModel,
    onNavigateToRegister: () -> Unit = {},
    onNavigateToForgotPassword: () -> Unit = {},
    onLoginSuccess: () -> Unit = {}
) {
    var usuario by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
    val loginError by viewModel.loginError.collectAsState()
    val loginCargando by viewModel.loginCargando.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF2D2D30))
            .padding(24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(40.dp))

            // Logo
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .background(Color.White, RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(R.drawable.rectivo_logo_sintexto),
                    contentDescription = "Logo sin texto",
                    modifier = Modifier.size(120.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "recTivo",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFFF5722)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Tu Tienda de Armarios\nModerna y Elegante",
                fontSize = 14.sp,
                color = Color(0xFFFF5722),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Usuario",
                fontSize = 14.sp,
                color = Color(0xFFFF5722),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = usuario,
                onValueChange = { usuario = it },
                placeholder = { Text("Escribe aquí tu usuario") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(25.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedBorderColor = Color(0xFFFF5722),
                    unfocusedBorderColor = Color(0xFFFF5722)
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Contraseña",
                fontSize = 14.sp,
                color = Color(0xFFFF5722),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = contrasena,
                onValueChange = { contrasena = it },
                placeholder = { Text("Escribe aquí tu contraseña") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(25.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedBorderColor = Color(0xFFFF5722),
                    unfocusedBorderColor = Color(0xFFFF5722)
                )
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    viewModel.login(usuario, contrasena, onExito = onLoginSuccess)
                },
                enabled = !loginCargando,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(25.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFE0E0E0),
                    disabledContainerColor = Color(0xFF888888)
                )
            ) {
                if (loginCargando) {
                    CircularProgressIndicator(
                        color = Color(0xFFFF5722),
                        modifier = Modifier.size(20.dp),
                        strokeWidth = 2.dp
                    )
                } else {
                    Text(
                        text = "Iniciar sesión",
                        color = Color(0xFFFF5722),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            // Mensaje de error
            if (loginError.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = loginError,
                    color = Color(0xFFFF5555),
                    fontSize = 13.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            TextButton(onClick = onNavigateToForgotPassword) {
                Text(
                    text = "¿Olvidaste tu contraseña?",
                    color = Color(0xFFFF5722),
                    fontSize = 12.sp
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier,
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "¿No tienes una cuenta? ",
                    color = Color(0xFFFF5722),
                    fontSize = 12.sp
                )
                TextButton(
                    onClick = onNavigateToRegister,
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text(
                        text = "Regístrate",
                        color = Color(0xFFFF5722),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}