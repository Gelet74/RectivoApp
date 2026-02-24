package com.example.rectivoapp.conexion

import com.example.rectivoapp.modelo.Cliente
import com.example.rectivoapp.modelo.ClienteUpdateRequest
import com.example.rectivoapp.modelo.LoginRequest
import com.example.rectivoapp.modelo.Producto
import retrofit2.Response
import retrofit2.http.*

interface RectivoAplicacionApi {

    @GET("articulos")
    suspend fun obtenerProductos(): List<Producto>

    @GET("clientes")
    suspend fun obtenerClientes(): List<Cliente>

    @POST("cliente/login")
    suspend fun login(@Body credenciales: LoginRequest): Cliente

    @PUT("clientes/{id}")
    suspend fun actualizarCliente(
        @Path("id") id: Int,
        @Body datos: ClienteUpdateRequest): Response<Unit>
}