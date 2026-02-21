package com.example.rectivoapp.ui.navegacion

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.rectivoapp.ui.pantallas.Login
import com.example.rectivoapp.ui.pantallas.PantallaArmario1Puerta
import com.example.rectivoapp.ui.pantallas.PantallaHome

object Rutas {
    const val LOGIN = "login"
    const val HOME = "home"
    const val ARMARIO_1_PUERTA = "armario1puerta"
}

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Rutas.LOGIN
    ) {
        composable(Rutas.LOGIN) {
            Login(
                onLoginSuccess = {
                    navController.navigate(Rutas.HOME) {
                        popUpTo(Rutas.LOGIN) { inclusive = true }
                    }
                }
            )
        }

        composable(Rutas.HOME) {
            PantallaHome(
                onCategoria1Puerta = {
                    navController.navigate(Rutas.ARMARIO_1_PUERTA)
                },
                onCategoria2Puertas = {
                    // TODO
                }
            )
        }

        composable(Rutas.ARMARIO_1_PUERTA) {
            PantallaArmario1Puerta(
                onDerecha = { /* TODO */ },
                onIzquierda = { /* TODO */ }
            )
        }
    }
}