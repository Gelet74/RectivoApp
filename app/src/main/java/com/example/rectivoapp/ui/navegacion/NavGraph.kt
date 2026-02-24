package com.example.rectivoapp.ui.navegacion

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.rectivoapp.ui.pantallas.*
import com.example.rectivoapp.ui.RectivoViewModel

object Rutas {

    const val LOGIN = "login"
    const val HOME = "home"
    const val SELECCION_PUERTAS = "seleccionpuertas"
    const val ARMARIO_1_PUERTA = "armario1puerta"
    const val SELECCION_MEDIDA = "seleccionmedida/{puertas}"
    const val SELECCION_COLOR_ESTRUCTURA = "seleccioncolorestructura"
    const val PERFIL = "perfil"
    const val CONTACTO = "contacto"
    const val SELECCION_CANTIDAD = "seleccioncantidad"
    const val RESUMEN_PEDIDO = "resumenpedido"
}

@Composable
fun NavGraph(
    navController: NavHostController,
    viewModel: RectivoViewModel
) {
    NavHost(
        navController = navController,
        startDestination = Rutas.LOGIN
    ) {

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
                onRealizarPedido = {
                    viewModel.resetearSeleccion()
                    navController.navigate(Rutas.SELECCION_PUERTAS)
                },
                onMisPedidos = { /* TODO */ },
                onMiPerfil = { navController.navigate(Rutas.PERFIL) },
                onContacto = { navController.navigate(Rutas.CONTACTO) }
            )
        }

        composable(Rutas.SELECCION_PUERTAS) {
            PantallaSeleccionPuertas(
                onVolver = { navController.popBackStack() },
                on1Puerta = { navController.navigate(Rutas.ARMARIO_1_PUERTA) },
                on2Puertas = {
                    val seleccionActual = viewModel.seleccionPedido.value
                    viewModel.actualizarSeleccion(
                        seleccionActual.copy(puertas = 2, apertura = null)
                    )
                    navController.navigate("seleccionmedida/2")
                }
            )
        }

        composable(Rutas.ARMARIO_1_PUERTA) {
            PantallaArmario1Puerta(
                onVolver = { navController.popBackStack() },
                onDerecha = {
                    val seleccionActual = viewModel.seleccionPedido.value
                    viewModel.actualizarSeleccion(
                        seleccionActual.copy(puertas = 1, apertura = "derecha")
                    )
                    navController.navigate("seleccionmedida/1")
                },
                onIzquierda = {
                    val seleccionActual = viewModel.seleccionPedido.value
                    viewModel.actualizarSeleccion(
                        seleccionActual.copy(puertas = 1, apertura = "izquierda")
                    )
                    navController.navigate("seleccionmedida/1")
                }
            )
        }

        composable(Rutas.RESUMEN_PEDIDO) {
            val seleccion by viewModel.seleccionPedido.collectAsState()
            PantallaResumenPedido(
                seleccion = seleccion,
                onVolver = { navController.popBackStack() },
                onConfirmar = {
                    // TODO - enviar pedido al servidor
                }
            )
        }

        composable(
            route = Rutas.SELECCION_MEDIDA,
            arguments = listOf(navArgument("puertas") { type = NavType.IntType })
        ) { backStackEntry ->
            val puertas = backStackEntry.arguments?.getInt("puertas") ?: 1
            PantallaSeleccionMedida(
                puertas = puertas,
                onVolver = { navController.popBackStack() },
                onMedidaSeleccionada = { medida ->
                    val seleccionActual = viewModel.seleccionPedido.value
                    viewModel.actualizarSeleccion(
                        seleccionActual.copy(medida = medida)
                    )
                    navController.navigate(Rutas.SELECCION_COLOR_ESTRUCTURA)
                }
            )
        }

        composable(Rutas.SELECCION_COLOR_ESTRUCTURA) {
            PantallaSeleccionColor(
                onVolver = { navController.popBackStack() },
                onColoresSeleccionados = { estructura, puerta ->
                    val seleccionActual = viewModel.seleccionPedido.value
                    viewModel.actualizarSeleccion(
                        seleccionActual.copy(
                            colorEstructura = estructura,
                            colorPuerta = puerta
                        )
                    )
                    navController.navigate(Rutas.SELECCION_CANTIDAD)
                }
            )
        }

        composable(Rutas.SELECCION_CANTIDAD) {
            PantallaSeleccionCantidad(
                onVolver = { navController.popBackStack() },
                onCantidadSeleccionada = { cantidad ->
                    val seleccionActual = viewModel.seleccionPedido.value
                    viewModel.actualizarSeleccion(
                        seleccionActual.copy(cantidad = cantidad)
                    )
                    navController.navigate(Rutas.RESUMEN_PEDIDO)
                }
            )
        }

        composable(Rutas.PERFIL) {
            val cliente by viewModel.cliente.collectAsState()
            cliente?.let {
                PantallaPerfil(
                    viewModel = viewModel,
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

        composable(Rutas.CONTACTO) {
            PantallaContacto(
                onVolver = { navController.popBackStack() }
            )
        }
    }
}