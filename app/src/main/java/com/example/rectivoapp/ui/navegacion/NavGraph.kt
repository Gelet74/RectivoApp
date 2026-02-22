package com.example.rectivoapp.ui.navegacion

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.rectivoapp.ui.pantallas.*
import com.example.rectivoapp.ui.RectivoViewModel

object Rutas {
    const val SPLASH = "splash"
    const val LOGIN = "login"
    const val HOME = "home"
    const val ARMARIO_1_PUERTA = "armario1puerta"
    const val PERFIL = "perfil"
    const val CONTACTO = "contacto"
    const val SELECCION_PUERTAS = "seleccionpuertas"
}

@Composable
fun NavGraph(
    navController: NavHostController,
    viewModel: RectivoViewModel
) {
    NavHost(
        navController = navController,
        startDestination = Rutas.SPLASH  // ← cambia esto
    ) {
        composable(Rutas.SPLASH) {
            PantallaInicio(
                onFinish = {
                    navController.navigate(Rutas.LOGIN) {
                        popUpTo(Rutas.SPLASH) { inclusive = true }
                    }
                }
            )
        }

        composable(Rutas.LOGIN) {
            Login(
                viewModel = viewModel,
                onLoginSuccess = {
                    navController.navigate(Rutas.HOME) {
                        popUpTo(Rutas.LOGIN) { inclusive = true }
                    }
                }
            )
        }

        composable(Rutas.HOME) {
            PantallaHome(
                onMisPedidos = { /* TODO */ },
                onMiPerfil = { navController.navigate(Rutas.PERFIL) },
                onContacto = { navController.navigate(Rutas.CONTACTO)},
                onRealizarPedido = { navController.navigate(Rutas.SELECCION_PUERTAS) }
            )
        }

        composable(Rutas.SELECCION_PUERTAS) {
            PantallaSeleccionPuertas(
                onVolver = { navController.popBackStack() },
                on1Puerta = { navController.navigate(Rutas.ARMARIO_1_PUERTA) },
                on2Puertas = { /* TODO */ }
            )
        }

        composable(Rutas.PERFIL) {
            val cliente by viewModel.cliente.collectAsState()
            cliente?.let {
                PantallaPerfil(
                    clienteId = it.id,
                    nombreInicial = it.nombre,
                    apellido1Inicial = it.apellido1,
                    apellido2Inicial = it.apellido2,
                    dniInicial = it.dni,
                    telefonoInicial = it.telefono,
                    onVolver = { navController.popBackStack() }
                )
            }
        }
        composable (Rutas.CONTACTO) {
            PantallaContacto(
                onVolver = { navController.popBackStack() }
            )
        }
    }
}