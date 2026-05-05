package com.example.rectivoapp.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.rectivoapp.modelo.PedidoBD

@Dao
interface PedidoDao {

    @Query("SELECT * FROM PedidoBD WHERE id = :id")
    suspend fun obtenerPedido(id: Int): PedidoBD

    @Query("SELECT * FROM PedidoBD WHERE usuarioId = :usuarioId ORDER BY id DESC")
    suspend fun obtenerPedidosPorUsuario(usuarioId: Int): List<PedidoBD>

    @Query("SELECT * FROM PedidoBD ORDER BY id DESC")
    suspend fun obtenerTodos(): List<PedidoBD>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertar(pedidoBD: PedidoBD)

    @Query("DELETE FROM PedidoBD WHERE id = :id")
    suspend fun eliminar(id: Int)
}
