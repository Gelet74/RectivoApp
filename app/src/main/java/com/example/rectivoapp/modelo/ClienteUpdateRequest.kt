package com.example.rectivoapp.modelo

import kotlinx.serialization.Serializable

@Serializable
data class ClienteUpdateRequest(
    val nombre: String,
    val apellido1: String,
    val apellido2: String,
    val dni: String,
    val telefono: String
)