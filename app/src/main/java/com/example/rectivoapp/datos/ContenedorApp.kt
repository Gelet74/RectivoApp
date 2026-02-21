package com.example.rectivoapp.datos

import com.example.rectivoapp.conexion.RectivoAplicacionApi
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import kotlin.getValue


interface ContenedorApp {
    val productoRepositorio: ProductoRepositorio

}

class ProductoContenedorApp : ContenedorApp {

    private val baseUrl = "http://192.168.0.25:3000/"



    private val json = Json { ignoreUnknownKeys = true }

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()

    private val servicioRetrofit: RectivoAplicacionApi by lazy {
        retrofit.create(RectivoAplicacionApi::class.java)
    }

    override val productoRepositorio: ProductoRepositorio by lazy {
        ConexionProductoRepositorio(servicioRetrofit)
    }

}