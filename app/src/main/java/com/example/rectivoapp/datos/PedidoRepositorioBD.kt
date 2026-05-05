package com.example.rectivoapp.datos

import com.example.rectivoapp.dao.PedidoDao
import com.example.rectivoapp.modelo.PedidoBD

interface PedidoRepositorioBD {
    suspend fun obtenerPedido(id: Int): PedidoBD
    suspend fun obtenerPedidosPorUsuario(usuarioId: Int): List<PedidoBD>
    suspend fun obtenerTodos(): List<PedidoBD>
    suspend fun insertar(pedidoBD: PedidoBD)
    suspend fun eliminar(id: Int)
}

class ConexionPedidoRepositorioBD(
    private val pedidoDao: PedidoDao
) : PedidoRepositorioBD {
    override suspend fun obtenerPedido(id: Int) = pedidoDao.obtenerPedido(id)
    override suspend fun obtenerPedidosPorUsuario(usuarioId: Int): List<PedidoBD> =
        pedidoDao.obtenerPedidosPorUsuario(usuarioId)
    override suspend fun obtenerTodos(): List<PedidoBD> = pedidoDao.obtenerTodos()
    override suspend fun insertar(pedidoBD: PedidoBD) = pedidoDao.insertar(pedidoBD)
    override suspend fun eliminar(id: Int) = pedidoDao.eliminar(id)
}
