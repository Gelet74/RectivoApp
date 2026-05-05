package com.example.rectivoapp.modelo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "PedidoBD")
data class PedidoBD(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val usuarioId: Int = 0,
    val productoId: Int = 0,
    val productoDescripcion: String = "",
    val productoCodigo: String = "",
    val cantidad: Int = 0,
    val precioUnitario: Double = 0.0,
    val precioTotal: Double = 0.0,
    val fecha: String = ""
)
