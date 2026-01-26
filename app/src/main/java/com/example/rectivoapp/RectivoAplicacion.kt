package com.example.rectivoapp

import android.app.Application
import com.example.rectivoapp.datos.ContenedorApp
import com.example.rectivoapp.datos.ProductoContenedorApp

class SimulacroAplicacion : Application() {
    lateinit var contenedor : ContenedorApp
    override fun onCreate() {
        super.onCreate()
        contenedor = ProductoContenedorApp()
    }
}