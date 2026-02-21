package com.example.rectivoapp.modelo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Producto(
    val id: Int,
    val codigo: String,
    val descripcion: String,
    val descripcion2: String,
    @SerialName("pvp") val precio: Double,
    val stock: Int,
    val imagenUrl: String? = null
)
