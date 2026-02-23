package com.example.rectivoapp.modelo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Cliente(
    val id: Int,
    val nombre: String,
    val apellido1: String,
    val apellido2: String,
    @SerialName("num_factura") val numfacturar: Int? = 0,
    @SerialName("num_pedido") val numpedido: Int? = 0,
    val dni: String,
    val telefono: String,
    val username: String
)