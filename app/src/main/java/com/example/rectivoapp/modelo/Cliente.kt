package com.example.rectivoapp.modelo

data class Cliente(
    val id: Int,
    val nombre: String,
    val apellido1: String,
    val apellido2: String,
    val numfacturar: Int,
    val numpedido: Int,
    val DNI: String,
    val tfono: String,
)
