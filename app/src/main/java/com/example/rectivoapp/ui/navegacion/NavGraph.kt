package com.example.rectivoapp.ui.navegacion

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.rectivoapp.ui.RectivoViewModel
import com.example.rectivoapp.ui.pantallas.*

object Rutas {
    const val LOGIN = "login"
    const val HOME = "home"
    const val REGISTRO = "registro"
    const val RECUPERAR_PASSWORD = "recuperarpassword"
    const val SELECCION_PUERTAS = "seleccionpuertas"
    const val ARMARIO_1_PUERTA = "armario1puerta"
    const val SELECCION_MEDIDA = "seleccionmedida/{puertas}"
    const val SELECCION_COLOR_ESTRUCTURA = "seleccioncolorestructura"
    const val PERFIL = "perfil"
    const val CONTACTO = "contacto"
    const val SELECCION_CANTIDAD = "seleccioncantidad"
    const val RESUMEN_PEDIDO = "resumenpedido"
    const val MIS_PEDIDOS = "mispedidos"
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
                onLoginSuccess = {
                    navController.navigate(Rutas.HOME) {
                        popUpTo(Rutas.LOGIN) { inclusive = true }
                    }
                },
                onNavigateToRegister = { navController.navigate(Rutas.REGISTRO) },
                onNavigateToForgotPassword = { navController.navigate(Rutas.RECUPERAR_PASSWORD) }
            )
        }

        composable(Rutas.REGISTRO) {
            PantallaRegistro(
                viewModel = viewModel,
                onRegistroExito = {
                    navController.navigate(Rutas.HOME) {
                        popUpTo(Rutas.LOGIN) { inclusive = true }
                    }
                },
                onVolver = { navController.popBackStack() }
            )
        }

        composable(Rutas.RECUPERAR_PASSWORD) {
            PantallaRecuperarPassword(
                viewModel = viewModel,
                onExito = { navController.popBackStack() },
                onVolver = { navController.popBackStack() }
            )
        }

        composable(Rutas.HOME) {
            PantallaHome(
                onRealizarPedido = { navController.navigate(Rutas.SELECCION_PUERTAS) },
                onMisPedidos = { navController.navigate(Rutas.MIS_PEDIDOS) },
                onMiPerfil = { navController.navigate(Rutas.PERFIL) },
                onContacto = { navController.navigate(Rutas.CONTACTO) },
                onCerrarSesion = {
                    viewModel.cerrarSesion()
                    navController.navigate(Rutas.LOGIN) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }

        composable(Rutas.MIS_PEDIDOS) {
            val pedidos by viewModel.pedidosMisPedidos.collectAsState()
            val cargando by viewModel.pedidosCargando.collectAsState()
            val modoOffline by viewModel.pedidosModoOffline.collectAsState()

            LaunchedEffect(Unit) { viewModel.cargarPedidosCliente() }

            PantallaMisPedidos(
                pedidos = pedidos,
                cargando = cargando,
                modoOffline = modoOffline,
                onVolver = { navController.popBackStack() }
            )
        }

        composable(Rutas.SELECCION_PUERTAS) {
            PantallaSeleccionPuertas(
                onVolver = { navController.popBackStack() },
                on1Puerta = { navController.navigate(Rutas.ARMARIO_1_PUERTA) },
                on2Puertas = {
                    viewModel.actualizarSeleccion(
                        viewModel.seleccionPedido.value.copy(puertas = 2, apertura = null)
                    )
                    navController.navigate("seleccionmedida/2")
                }
            )
        }

        composable(Rutas.ARMARIO_1_PUERTA) {
            PantallaArmario1Puerta(
                onVolver = { navController.popBackStack() },
                onDerecha = {
                    viewModel.actualizarSeleccion(
                        viewModel.seleccionPedido.value.copy(puertas = 1, apertura = "derecha")
                    )
                    navController.navigate("seleccionmedida/1")
                },
                onIzquierda = {
                    viewModel.actualizarSeleccion(
                        viewModel.seleccionPedido.value.copy(puertas = 1, apertura = "izquierda")
                    )
                    navController.navigate("seleccionmedida/1")
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
                    viewModel.actualizarSeleccion(
                        viewModel.seleccionPedido.value.copy(medida = medida)
                    )
                    navController.navigate(Rutas.SELECCION_COLOR_ESTRUCTURA)
                }
            )
        }

        composable(Rutas.SELECCION_COLOR_ESTRUCTURA) {
            PantallaSeleccionColor(
                onVolver = { navController.popBackStack() },
                onColoresSeleccionados = { estructura, puerta ->
                    viewModel.actualizarSeleccion(
                        viewModel.seleccionPedido.value.copy(
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
                    viewModel.actualizarSeleccion(
                        viewModel.seleccionPedido.value.copy(cantidad = cantidad)
                    )
                    navController.navigate(Rutas.RESUMEN_PEDIDO)
                }
            )
        }

        composable(Rutas.RESUMEN_PEDIDO) {
            val seleccion by viewModel.seleccionPedido.collectAsState()
            val carrito by viewModel.carrito.collectAsState()

            LaunchedEffect(seleccion.generarCodigo()) {
                viewModel.resolverPrecio(seleccion.generarCodigo())
            }

            PantallaResumenPedido(
                seleccion = seleccion,
                carrito = carrito,
                viewModel = viewModel,
                onVolver = { navController.popBackStack() },
                onConfirmar = { fecha ->
                    viewModel.confirmarPedido(
                        fechaEntrega = fecha,
                        onExito = {
                            navController.navigate(Rutas.HOME) {
                                popUpTo(Rutas.HOME) { inclusive = false }
                            }
                        }
                    )
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