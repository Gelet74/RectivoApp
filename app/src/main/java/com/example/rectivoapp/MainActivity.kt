package com.example.rectivoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.navigation.compose.rememberNavController
import com.example.rectivoapp.ui.navegacion.NavGraph
import com.example.rectivoapp.ui.RectivoViewModel
import com.rectivo.ui.theme.RectivoTheme

class MainActivity : ComponentActivity() {

    private val rectivoViewModel: RectivoViewModel by viewModels {
        val app = application as RectivoAplicacion
        RectivoViewModel.factory(app.contenedor.clienteRepositorio)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            RectivoTheme {
                val navController = rememberNavController()
                NavGraph(
                    navController = navController,
                    viewModel = rectivoViewModel
                )
            }
        }
    }
}