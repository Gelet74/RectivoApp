package com.example.rectivoapp.modelo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PedidoRequest(
    @SerialName("id_cliente") val idCliente: Int,
    @SerialName("codigo_articulo") val codigoArticulo: String,
    val cantidad: Int,
    @SerialName("fecha_entrega") val fechaEntrega: String
)