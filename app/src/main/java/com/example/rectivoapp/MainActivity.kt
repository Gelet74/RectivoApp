package com.example.rectivoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.example.rectivoapp.ui.navegacion.NavGraph
import com.rectivo.ui.theme.RectivoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            RectivoTheme {
                val navController = rememberNavController()
                NavGraph(navController = navController)
            }
        }
    }
}
