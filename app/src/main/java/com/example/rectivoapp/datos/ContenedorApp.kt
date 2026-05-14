package com.example.rectivoapp.datos

import android.content.Context
import com.example.rectivoapp.conexion.ConexionBD
import com.example.rectivoapp.conexion.RectivoAplicacionApi
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface ContenedorApp {
    val productoRepositorio: ProductoRepositorio
    val pedidoRepositorioBD: PedidoRepositorioBD
    val clienteRepositorio: ClienteRepositorio
    val pedidoRepositorio: PedidoRepositorio
}

class ProductoContenedorApp(private val context: Context) : ContenedorApp {

    private val baseUrl = "http://10.0.2.2:8080/"

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

    override val pedidoRepositorioBD: PedidoRepositorioBD by lazy {
        ConexionPedidoRepositorioBD(ConexionBD.obtenerBD(context).pedidoDao())
    }

    override val clienteRepositorio: ClienteRepositorio by lazy {
        ConexionClienteRepositorio(servicioRetrofit)
    }

    override val pedidoRepositorio: PedidoRepositorio by lazy {
        ConexionPedidoRepositorio(servicioRetrofit)
    }
}
