package com.example.rectivoapp.modelo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Pedido(
    @SerialName("id") val id: Int = 0,
    @SerialName("idCliente") val idCliente: Int = 0,
    @SerialName("fechaPedido") val fechaPedido: String = "",
    @SerialName("fechaEntrega") val fechaEntrega: String? = null,
    @SerialName("estado") val estado: String = "",
    @SerialName("total") val total: Double = 0.0
)