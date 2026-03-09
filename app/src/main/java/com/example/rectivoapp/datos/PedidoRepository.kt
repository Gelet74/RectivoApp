package com.example.rectivoapp.datos

import com.example.rectivoapp.conexion.RectivoAplicacionApi
import com.example.rectivoapp.modelo.Pedido
import com.example.rectivoapp.modelo.PedidoRequest
import retrofit2.Response

interface PedidoRepositorio {
    suspend fun crearPedido(request: PedidoRequest): Response<Unit>
    suspend fun getPedidosByCliente(idCliente: Int): List<Pedido>
}

class ConexionPedidoRepositorio(
    private val api: RectivoAplicacionApi
) : PedidoRepositorio {
    override suspend fun crearPedido(request: PedidoRequest): Response<Unit> =
        api.crearPedido(request)
    override suspend fun getPedidosByCliente(idCliente: Int): List<Pedido> =
        api.getPedidosByCliente(idCliente)
}