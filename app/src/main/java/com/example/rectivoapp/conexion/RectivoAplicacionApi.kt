package com.example.rectivoapp.conexion

import com.example.rectivoapp.modelo.Cliente
import com.example.rectivoapp.modelo.ClienteUpdateRequest
import com.example.rectivoapp.modelo.LoginRequest
import com.example.rectivoapp.modelo.Pedido
import com.example.rectivoapp.modelo.PedidoRequest
import com.example.rectivoapp.modelo.Producto
import retrofit2.Response
import retrofit2.http.*

interface RectivoAplicacionApi {

    @GET("articulos")
    suspend fun obtenerProductos(): List<Producto>

    @GET("articulos/codigo/{codigo}")
    suspend fun obtenerProductoPorCodigo(@Path("codigo") codigo: String): Producto

    @GET("clientes")
    suspend fun obtenerClientes(): List<Cliente>

    @POST("clientes/login")
    suspend fun login(@Body credenciales: LoginRequest): Cliente

    @PUT("clientes/{id}")
    suspend fun actualizarCliente(
        @Path("id") id: Int,
        @Body datos: ClienteUpdateRequest): Response<Unit>

    @POST("clientes/registro")
    suspend fun registro(@Body cliente: Cliente): Cliente

    @PUT("clientes/recuperar")
    suspend fun recuperarPassword(
        @Query("dni") dni: String,
        @Query("nuevaPassword") nuevaPassword: String
    ): Response<Unit>

    @POST("pedidos")
    suspend fun crearPedido(@Body request: PedidoRequest): Response<Unit>

    @GET("pedidos/cliente/{idCliente}")
    suspend fun getPedidosByCliente(@Path("idCliente") idCliente: Int): List<Pedido>
}