package com.example.rectivoapp.modelo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Cliente(
    val id: Int = 0,
    val nombre: String = "",
    val apellido1: String = "",
    val apellido2: String = "",
    @SerialName("num_factura") val numFactura: Int? = 0,
    @SerialName("num_pedido") val numPedido: Int? = 0,
    val dni: String = "",
    val telefono: String = "",
    val username: String = "",
    val password: String = ""
)