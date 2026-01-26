package com.example.rectivoapp.modelo


data class Producto(
    val id: Int,
    val codigo: String,
    val descripcion: String,
    val descripcion2: String,
    val precio: Double,
    val stock: Int,
    val imagenUrl: String? = null )