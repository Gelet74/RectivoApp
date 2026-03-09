package com.example.rectivoapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.rectivoapp.datos.ClienteRepositorio
import com.example.rectivoapp.datos.PedidoRepositorio
import com.example.rectivoapp.modelo.Cliente
import com.example.rectivoapp.modelo.ClienteUpdateRequest
import com.example.rectivoapp.modelo.Pedido
import com.example.rectivoapp.modelo.PedidoRequest
import com.example.rectivoapp.modelo.Producto
import com.example.rectivoapp.modelo.SeleccionPedido
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RectivoViewModel(
    private val clienteRepositorio: ClienteRepositorio,
    private val pedidoRepositorio: PedidoRepositorio

) : ViewModel() {

    // ── Cliente ──
    private val _cliente = MutableStateFlow<Cliente?>(null)
    val cliente: StateFlow<Cliente?> = _cliente

    fun guardarCliente(cliente: Cliente) {
        _cliente.value = cliente
    }

    fun cerrarSesion() {
        _cliente.value = null
    }

    // ── Login ──
    private val _loginError = MutableStateFlow<String>("")
    val loginError: StateFlow<String> = _loginError

    private val _loginCargando = MutableStateFlow(false)
    val loginCargando: StateFlow<Boolean> = _loginCargando

    fun login(username: String, password: String, onExito: () -> Unit) {
        viewModelScope.launch {
            _loginCargando.value = true
            _loginError.value = ""
            try {
                val cliente = clienteRepositorio.login(username, password)
                guardarCliente(cliente)
                onExito()
            } catch (e: Exception) {
                _loginError.value = "Usuario o contraseña incorrectos."
            } finally {
                _loginCargando.value = false
            }
        }
    }

    // ── Actualizar perfil ──
    private val _perfilMensaje = MutableStateFlow("")
    val perfilMensaje: StateFlow<String> = _perfilMensaje

    private val _perfilCargando = MutableStateFlow(false)
    val perfilCargando: StateFlow<Boolean> = _perfilCargando

    fun actualizarPerfil(id: Int, datos: ClienteUpdateRequest) {
        viewModelScope.launch {
            _perfilCargando.value = true
            _perfilMensaje.value = ""
            try {
                val respuesta = clienteRepositorio.actualizarCliente(id, datos)
                _perfilMensaje.value = if (respuesta.isSuccessful)
                    "Datos actualizados correctamente ✓"
                else
                    "Error al actualizar (${respuesta.code()})"
            } catch (e: Exception) {
                _perfilMensaje.value = "Error: no se pudo conectar al servidor"
            } finally {
                _perfilCargando.value = false
            }
        }
    }

    // ── Mis Pedidos ──
    private val _misPedidos = MutableStateFlow<List<Pedido>>(emptyList())
    val misPedidos: StateFlow<List<Pedido>> = _misPedidos

    private val _misPedidosCargando = MutableStateFlow(false)
    val misPedidosCargando: StateFlow<Boolean> = _misPedidosCargando

    fun cargarMisPedidos() {
        val cliente = _cliente.value ?: return
        viewModelScope.launch {
            _misPedidosCargando.value = true
            try {
                _misPedidos.value = pedidoRepositorio.getPedidosByCliente(cliente.id)
            } catch (e: Exception) {
                _misPedidos.value = emptyList()
            } finally {
                _misPedidosCargando.value = false
            }
        }
    }

    // ── Productos ──
    private val _productos = MutableStateFlow<List<Producto>>(emptyList())
    val productos: StateFlow<List<Producto>> = _productos

    fun guardarProductos(lista: List<Producto>) {
        _productos.value = lista
    }


    // ── Registro ──
    private val _registroError = MutableStateFlow("")
    val registroError: StateFlow<String> = _registroError

    private val _registroCargando = MutableStateFlow(false)
    val registroCargando: StateFlow<Boolean> = _registroCargando

    fun registro(cliente: Cliente, onExito: () -> Unit) {
        viewModelScope.launch {
            _registroCargando.value = true
            _registroError.value = ""
            try {
                val clienteRegistrado = clienteRepositorio.registro(cliente)
                guardarCliente(clienteRegistrado)
                onExito()
            } catch (e: Exception) {
                _registroError.value = "Error al registrarse. Inténtalo de nuevo."
            } finally {
                _registroCargando.value = false
            }
        }
    }

    // ── Recuperar contraseña ──
    private val _recuperarMensaje = MutableStateFlow("")
    val recuperarMensaje: StateFlow<String> = _recuperarMensaje

    private val _recuperarCargando = MutableStateFlow(false)
    val recuperarCargando: StateFlow<Boolean> = _recuperarCargando

    fun recuperarPassword(dni: String, nuevaPassword: String, onExito: () -> Unit) {
        viewModelScope.launch {
            _recuperarCargando.value = true
            _recuperarMensaje.value = ""
            try {
                val respuesta = clienteRepositorio.recuperarPassword(dni, nuevaPassword)
                if (respuesta.isSuccessful) {
                    onExito()
                } else {
                    _recuperarMensaje.value = "DNI no encontrado."
                }
            } catch (e: Exception) {
                _recuperarMensaje.value = "Error al conectar con el servidor."
            } finally {
                _recuperarCargando.value = false
            }
        }
    }

    // ── Pedido ──
    private val _seleccionPedido = MutableStateFlow(SeleccionPedido())
    val seleccionPedido: StateFlow<SeleccionPedido> = _seleccionPedido

    fun actualizarSeleccion(seleccion: SeleccionPedido) {
        _seleccionPedido.value = seleccion
    }

    fun resetearSeleccion() {
        _seleccionPedido.value = SeleccionPedido()
    }

    // ── Pedido ──
    private val _pedidoMensaje = MutableStateFlow("")
    val pedidoMensaje: StateFlow<String> = _pedidoMensaje

    private val _pedidoCargando = MutableStateFlow(false)
    val pedidoCargando: StateFlow<Boolean> = _pedidoCargando

    fun confirmarPedido(fechaEntrega: String, onExito: () -> Unit) {
        val cliente = _cliente.value ?: return
        val seleccion = _seleccionPedido.value
        val codigo = seleccion.generarCodigo()

        viewModelScope.launch {
            _pedidoCargando.value = true
            _pedidoMensaje.value = ""
            try {
                val request = PedidoRequest(
                    idCliente = cliente.id,
                    codigoArticulo = codigo,
                    cantidad = seleccion.cantidad,
                    fechaEntrega = fechaEntrega
                )
                val respuesta = pedidoRepositorio.crearPedido(request)
                if (respuesta.isSuccessful) {
                    resetearSeleccion()
                    onExito()
                } else {
                    _pedidoMensaje.value = "Error al confirmar el pedido (${respuesta.code()})"
                }
            } catch (e: Exception) {
                _pedidoMensaje.value = "Error al conectar con el servidor"
            } finally {
                _pedidoCargando.value = false
            }
        }
    }

    // ── Factory ──
    companion object {
        fun factory(
            clienteRepositorio: ClienteRepositorio,
            pedidoRepositorio: PedidoRepositorio
        ): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T =
                    RectivoViewModel(clienteRepositorio, pedidoRepositorio) as T
            }
    }
}