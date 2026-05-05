package com.example.rectivoapp.conexion

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.rectivoapp.dao.PedidoDao
import com.example.rectivoapp.modelo.PedidoBD

@Database(entities = [PedidoBD::class], version = 1, exportSchema = false)
abstract class ConexionBD : RoomDatabase() {

    abstract fun pedidoDao(): PedidoDao

    companion object {
        @Volatile
        private var Instance: ConexionBD? = null

        fun obtenerBD(context: Context): ConexionBD {
            return Instance ?: synchronized(lock = this) {
                Room.databaseBuilder(context, ConexionBD::class.java, "rectivodb")
                    .build()
                    .also { Instance = it }
            }
        }
    }
}
