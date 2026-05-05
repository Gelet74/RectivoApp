package com.example.rectivoapp.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.rectivoapp.RectivoAplicacion
import com.example.rectivoapp.datos.ClienteRepositorio
import com.example.rectivoapp.datos.PedidoRepositorioBD
import com.example.rectivoapp.datos.PedidoRepositorio
import com.example.rectivoapp.datos.ProductoRepositorio
import com.example.rectivoapp.modelo.Cliente
import com.example.rectivoapp.modelo.ClienteUpdateRequest
import com.example.rectivoapp.modelo.Pedido
import com.example.rectivoapp.modelo.PedidoBD
import com.example.rectivoapp.modelo.PedidoRequest
import com.example.rectivoapp.modelo.Producto
import com.example.rectivoapp.modelo.SeleccionPedido
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.IOException

sealed interface UIStateProductos {
    data class Exito(val productos: List<Producto>) : UIStateProductos
    object Error : UIStateProductos
    object Cargando : UIStateProductos
}

sealed interface UIStatePedidosBD {
    data class Exito(val pedidos: List<PedidoBD>) : UIStatePedidosBD
    object Error : UIStatePedidosBD
    object Cargando : UIStatePedidosBD
}

class RectivoViewModel(
    private val productoRepositorio: ProductoRepositorio,
    private val pedidoRepositorioBD: PedidoRepositorioBD,
    private val clienteRepositorio: ClienteRepositorio,
    private val pedidoRepositorio: PedidoRepositorio
) : ViewModel() {

    data class LineaCarrito(
        val seleccion: SeleccionPedido,
        val subtotal: Double = seleccion.precioUnitario * seleccion.cantidad
    )

    // ── Productos ──
    var uiStateProductos: UIStateProductos by mutableStateOf(UIStateProductos.Cargando)
        private set

    // ── Pedidos Room ──
    var uiStatePedidosBD: UIStatePedidosBD by mutableStateOf(UIStatePedidosBD.Cargando)
        private set

    // ── Cliente activo ──
    private val _cliente = MutableStateFlow<Cliente?>(null)
    val cliente: StateFlow<Cliente?> = _cliente.asStateFlow()

    // ── Selección pedido actual ──
    private val _seleccionPedido = MutableStateFlow(SeleccionPedido())
    val seleccionPedido: StateFlow<SeleccionPedido> = _seleccionPedido.asStateFlow()

    // ── Carrito temporal ──
    private val _carrito = MutableStateFlow<List<LineaCarrito>>(emptyList())
    val carrito: StateFlow<List<LineaCarrito>> = _carrito.asStateFlow()

    // ── Pedidos para MisPedidos (servidor con fallback a Room) ──
    private val _pedidosMisPedidos = MutableStateFlow<List<Pedido>>(emptyList())
    val pedidosMisPedidos: StateFlow<List<Pedido>> = _pedidosMisPedidos.asStateFlow()

    private val _pedidosCargando = MutableStateFlow(false)
    val pedidosCargando: StateFlow<Boolean> = _pedidosCargando.asStateFlow()

    // true = datos vinieron del servidor, false = vinieron de Room (sin conexión)
    private val _pedidosModoOffline = MutableStateFlow(false)
    val pedidosModoOffline: StateFlow<Boolean> = _pedidosModoOffline.asStateFlow()

    // ── Pedidos del servidor (mantenido por compatibilidad) ──
    private val _pedidosServidor = MutableStateFlow<List<Pedido>>(emptyList())
    val pedidosServidor: StateFlow<List<Pedido>> = _pedidosServidor.asStateFlow()

    // ── Login ──
    private val _loginError = MutableStateFlow("")
    val loginError: StateFlow<String> = _loginError.asStateFlow()

    private val _loginCargando = MutableStateFlow(false)
    val loginCargando: StateFlow<Boolean> = _loginCargando.asStateFlow()

    // ── Registro ──
    private val _registroError = MutableStateFlow("")
    val registroError: StateFlow<String> = _registroError.asStateFlow()

    private val _registroCargando = MutableStateFlow(false)
    val registroCargando: StateFlow<Boolean> = _registroCargando.asStateFlow()

    // ── Perfil ──
    private val _perfilMensaje = MutableStateFlow("")
    val perfilMensaje: StateFlow<String> = _perfilMensaje.asStateFlow()

    private val _perfilCargando = MutableStateFlow(false)
    val perfilCargando: StateFlow<Boolean> = _perfilCargando.asStateFlow()

    // ── Recuperar contraseña ──
    private val _recuperarMensaje = MutableStateFlow("")
    val recuperarMensaje: StateFlow<String> = _recuperarMensaje.asStateFlow()

    private val _recuperarCargando = MutableStateFlow(false)
    val recuperarCargando: StateFlow<Boolean> = _recuperarCargando.asStateFlow()

    init {
        obtenerProductos()
    }

    // ── Productos ──
    fun obtenerProductos() {
        viewModelScope.launch {
            uiStateProductos = UIStateProductos.Cargando
            uiStateProductos = try {
                UIStateProductos.Exito(productoRepositorio.obtenerProductos())
            } catch (e: Exception) {
                UIStateProductos.Error
            }
        }
    }

    // ── Selección pedido ──
    fun actualizarSeleccion(nueva: SeleccionPedido) {
        _seleccionPedido.value = nueva
    }

    fun resolverPrecio(codigo: String) {
        viewModelScope.launch {
            try {
                val producto = productoRepositorio.obtenerProductoPorCodigo(codigo)
                _seleccionPedido.value = _seleccionPedido.value.copy(precioUnitario = producto.precio)
            } catch (e: Exception) {
                android.util.Log.e("PRECIO", "Error al resolver precio para $codigo: ${e.message}")
            }
        }
    }

    // ── Carrito ──
    fun anadirAlCarrito(seleccion: SeleccionPedido) {
        _carrito.value = _carrito.value + LineaCarrito(seleccion)
    }

    fun limpiarCarrito() {
        _carrito.value = emptyList()
        _seleccionPedido.value = SeleccionPedido()
    }

    // ── Confirmar pedido: guarda en Room Y envía al servidor ──
    fun confirmarPedido(fechaEntrega: String, onExito: () -> Unit) {
        val seleccionActual = _seleccionPedido.value
        viewModelScope.launch {
            try {
                val todasLasLineas = _carrito.value + LineaCarrito(seleccionActual)

                // Guardar en Room
                todasLasLineas.forEach { linea ->
                    pedidoRepositorioBD.insertar(
                        PedidoBD(
                            usuarioId = _cliente.value?.id ?: 0,
                            productoId = 0,
                            productoDescripcion = linea.seleccion.generarCodigo(),
                            productoCodigo = linea.seleccion.generarCodigo(),
                            cantidad = linea.seleccion.cantidad,
                            precioUnitario = linea.seleccion.precioUnitario,
                            precioTotal = linea.subtotal,
                            fecha = fechaEntrega
                        )
                    )
                }

                // Enviar al servidor
                val idCliente = _cliente.value?.id ?: 0
                todasLasLineas.forEach { linea ->
                    pedidoRepositorio.crearPedido(
                        PedidoRequest(
                            idCliente = idCliente,
                            codigoArticulo = linea.seleccion.generarCodigo(),
                            cantidad = linea.seleccion.cantidad,
                            fechaEntrega = fechaEntrega
                        )
                    )
                }

                limpiarCarrito()
                onExito()
            } catch (e: Exception) {
                // si falla el servidor el pedido ya está en Room igualmente
                limpiarCarrito()
                onExito()
            }
        }
    }

    // ── Cargar pedidos para MisPedidos con fallback offline ──
    fun cargarPedidosCliente() {
        val idCliente = _cliente.value?.id ?: return
        viewModelScope.launch {
            _pedidosCargando.value = true
            try {
                // 1. Intentar cargar del servidor
                val pedidosApi = pedidoRepositorio.getPedidosByCliente(idCliente)

                // 2. Si hay datos del servidor, sincronizar Room con ellos
                sincronizarPedidosEnRoom(idCliente, pedidosApi)

                // 3. Mostrar los datos del servidor
                _pedidosMisPedidos.value = pedidosApi
                _pedidosServidor.value = pedidosApi
                _pedidosModoOffline.value = false

            } catch (e: Exception) {
                // 4. Sin conexión: cargar desde Room
                android.util.Log.w("PEDIDOS", "Sin conexión, cargando desde Room: ${e.message}")
                val pedidosLocales = pedidoRepositorioBD.obtenerPedidosPorUsuario(idCliente)

                // 5. Convertir PedidoBD → Pedido para reutilizar la UI existente
                _pedidosMisPedidos.value = pedidosLocales.map { bd ->
                    Pedido(
                        id = bd.id,
                        idCliente = bd.usuarioId,
                        fechaPedido = bd.fecha,
                        fechaEntrega = bd.fecha,
                        estado = "Sin conexión",
                        total = bd.precioTotal
                    )
                }
                _pedidosModoOffline.value = true
            } finally {
                _pedidosCargando.value = false
            }
        }
    }

    // Guarda en Room los pedidos recibidos del servidor para este usuario
    private suspend fun sincronizarPedidosEnRoom(idCliente: Int, pedidosApi: List<Pedido>) {
        try {
            pedidosApi.forEach { pedido ->
                pedidoRepositorioBD.insertar(
                    PedidoBD(
                        id = pedido.id,
                        usuarioId = idCliente,
                        productoId = 0,
                        productoDescripcion = "",
                        productoCodigo = "",
                        cantidad = 0,
                        precioUnitario = 0.0,
                        precioTotal = pedido.total,
                        fecha = pedido.fechaEntrega ?: pedido.fechaPedido
                    )
                )
            }
        } catch (e: Exception) {
            android.util.Log.e("ROOM_SYNC", "Error al sincronizar Room: ${e.message}")
        }
    }

    // ── Room pedidos ──
    fun obtenerPedidosBD() {
        viewModelScope.launch {
            uiStatePedidosBD = UIStatePedidosBD.Cargando
            uiStatePedidosBD = try {
                val idCliente = _cliente.value?.id ?: 0
                val pedidos = if (idCliente > 0)
                    pedidoRepositorioBD.obtenerPedidosPorUsuario(idCliente)
                else
                    pedidoRepositorioBD.obtenerTodos()
                UIStatePedidosBD.Exito(pedidos)
            } catch (e: Exception) {
                UIStatePedidosBD.Error
            }
        }
    }

    fun eliminarPedido(id: Int) {
        viewModelScope.launch {
            pedidoRepositorioBD.eliminar(id)
            obtenerPedidosBD()
        }
    }

    // ── Login ──
    fun login(username: String, password: String, onExito: () -> Unit) {
        viewModelScope.launch {
            _loginCargando.value = true
            _loginError.value = ""
            try {
                val cliente = clienteRepositorio.login(username, password)
                _cliente.value = cliente
                onExito()
            } catch (e: Exception) {
                _loginError.value = "Usuario o contraseña incorrectos"
            } finally {
                _loginCargando.value = false
            }
        }
    }

    fun cerrarSesion() {
        _cliente.value = null
        limpiarCarrito()
        _pedidosMisPedidos.value = emptyList()
        _pedidosModoOffline.value = false
    }

    // ── Registro ──
    fun registro(cliente: Cliente, onExito: () -> Unit) {
        viewModelScope.launch {
            _registroCargando.value = true
            _registroError.value = ""
            try {
                val clienteCreado = clienteRepositorio.registro(cliente)
                _cliente.value = clienteCreado
                onExito()
            } catch (e: Exception) {
                _registroError.value = "Error al crear la cuenta. Inténtalo de nuevo."
            } finally {
                _registroCargando.value = false
            }
        }
    }

    // ── Perfil ──
    fun actualizarPerfil(id: Int, datos: ClienteUpdateRequest) {
        viewModelScope.launch {
            _perfilCargando.value = true
            _perfilMensaje.value = ""
            try {
                clienteRepositorio.actualizarCliente(id, datos)
                _cliente.value = _cliente.value?.copy(
                    nombre = datos.nombre,
                    apellido1 = datos.apellido1,
                    apellido2 = datos.apellido2,
                    dni = datos.dni,
                    telefono = datos.telefono
                )
                _perfilMensaje.value = "Datos actualizados correctamente"
            } catch (e: Exception) {
                _perfilMensaje.value = "Error al actualizar los datos"
            } finally {
                _perfilCargando.value = false
            }
        }
    }

    // ── Recuperar contraseña ──
    fun recuperarPassword(dni: String, nuevaPassword: String, onExito: () -> Unit) {
        viewModelScope.launch {
            _recuperarCargando.value = true
            _recuperarMensaje.value = ""
            try {
                clienteRepositorio.recuperarPassword(dni, nuevaPassword)
                _recuperarMensaje.value = "Contraseña actualizada correctamente"
                onExito()
            } catch (e: Exception) {
                _recuperarMensaje.value = "DNI no encontrado o error en el servidor"
            } finally {
                _recuperarCargando.value = false
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val aplicacion = (this[APPLICATION_KEY] as RectivoAplicacion)
                RectivoViewModel(
                    productoRepositorio = aplicacion.contenedor.productoRepositorio,
                    pedidoRepositorioBD = aplicacion.contenedor.pedidoRepositorioBD,
                    clienteRepositorio = aplicacion.contenedor.clienteRepositorio,
                    pedidoRepositorio = aplicacion.contenedor.pedidoRepositorio
                )
            }
        }
    }
}