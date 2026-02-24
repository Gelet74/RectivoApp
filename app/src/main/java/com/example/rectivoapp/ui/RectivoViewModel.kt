package com.example.rectivoapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.rectivoapp.datos.ClienteRepositorio
import com.example.rectivoapp.modelo.Cliente
import com.example.rectivoapp.modelo.ClienteUpdateRequest
import com.example.rectivoapp.modelo.Producto
import com.example.rectivoapp.modelo.SeleccionPedido
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RectivoViewModel(
    private val clienteRepositorio: ClienteRepositorio
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

    // ── Productos ──
    private val _productos = MutableStateFlow<List<Producto>>(emptyList())
    val productos: StateFlow<List<Producto>> = _productos

    fun guardarProductos(lista: List<Producto>) {
        _productos.value = lista
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

    // ── Factory ──
    companion object {
        fun factory(clienteRepositorio: ClienteRepositorio): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T =
                    RectivoViewModel(clienteRepositorio) as T
            }
    }
}