package com.example.rectivoapp.datos

import com.example.rectivoapp.conexion.RectivoAplicacionApi
import com.example.rectivoapp.modelo.Cliente
import com.example.rectivoapp.modelo.ClienteUpdateRequest
import com.example.rectivoapp.modelo.LoginRequest
import retrofit2.Response

interface ClienteRepositorio {
    suspend fun login(username: String, password: String): Cliente
    suspend fun actualizarCliente(id: Int, datos: ClienteUpdateRequest): Response<Unit>
    suspend fun registro(cliente: Cliente): Cliente
    suspend fun recuperarPassword(dni: String, nuevaPassword: String): Response<Unit>
}

class ConexionClienteRepositorio(
    private val api: RectivoAplicacionApi
) : ClienteRepositorio {

    override suspend fun login(username: String, password: String): Cliente =
        api.login(LoginRequest(username, password))

    override suspend fun actualizarCliente(id: Int, datos: ClienteUpdateRequest): Response<Unit> =
        api.actualizarCliente(id, datos)

    override suspend fun registro(cliente: Cliente): Cliente =
        api.registro(cliente)

    override suspend fun recuperarPassword(dni: String, nuevaPassword: String): Response<Unit> =
        api.recuperarPassword(dni, nuevaPassword)
}