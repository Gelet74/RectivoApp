package com.example.rectivoapp.conexion

import com.example.rectivoapp.modelo.Cliente
import com.example.rectivoapp.modelo.Producto
import retrofit2.http.GET

interface RectivoAplicacionApi {

    @GET(value= "articulos")
    suspend fun obtenerProductos(): List<Producto>

    @GET(value= "clientes")
    suspend fun obtenerClientes(): List<Cliente>



}