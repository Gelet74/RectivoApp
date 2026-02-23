package com.example.rectivoapp.ui

import androidx.lifecycle.ViewModel
import com.example.rectivoapp.modelo.Cliente
import com.example.rectivoapp.modelo.Producto
import com.example.rectivoapp.modelo.SeleccionPedido
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class RectivoViewModel : ViewModel() {

    private val _cliente = MutableStateFlow<Cliente?>(null)
    val cliente: StateFlow<Cliente?> = _cliente

    fun guardarCliente(cliente: Cliente) {
        _cliente.value = cliente
    }

    fun cerrarSesion() {
        _cliente.value = null
    }

    private val _seleccionPedido = MutableStateFlow(SeleccionPedido())
    val seleccionPedido: StateFlow<SeleccionPedido> = _seleccionPedido

    fun actualizarSeleccion(seleccion: SeleccionPedido) {
        _seleccionPedido.value = seleccion
    }

    fun resetearSeleccion() {
        _seleccionPedido.value = SeleccionPedido()
    }

    private val _productos = MutableStateFlow<List<Producto>>(emptyList())
    val productos: StateFlow<List<Producto>> = _productos

    fun guardarProductos(lista: List<Producto>) {
        _productos.value = lista
    }
}