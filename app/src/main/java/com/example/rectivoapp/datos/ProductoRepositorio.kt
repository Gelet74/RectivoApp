package com.example.rectivoapp.datos

import com.example.rectivoapp.conexion.RectivoAplicacionApi
import com.example.rectivoapp.modelo.Cliente
import com.example.rectivoapp.modelo.Producto

interface ProductoRepositorio {
    suspend fun obtenerProductos(): List<Producto>
    suspend fun obtenerClientes(): List<Cliente>
    suspend fun obtenerProductoPorCodigo(codigo: String): Producto
}

class ConexionProductoRepositorio(
    private val productosServicioApi: RectivoAplicacionApi
) : ProductoRepositorio {
    override suspend fun obtenerProductos(): List<Producto> = productosServicioApi.obtenerProductos()
    override suspend fun obtenerClientes(): List<Cliente> = productosServicioApi.obtenerClientes()
    override suspend fun obtenerProductoPorCodigo(codigo: String): Producto =
        productosServicioApi.obtenerProductoPorCodigo(codigo)
}
