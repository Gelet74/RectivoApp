package com.example.rectivoapp.ui.pantallas

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import com.example.rectivoapp.R
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun PantallaInicio(
    onFinish: () -> Unit
) {
    val alpha = remember { Animatable(0f) }
    val scale = remember { Animatable(0.2f) }

    LaunchedEffect(Unit) {

        launch {
            alpha.animateTo(
                targetValue = 1f,
                animationSpec = tween(4000)
            )
        }
        launch {
            scale.animateTo(
                targetValue = 2F,
                animationSpec = tween(4000)
            )
        }

        delay(4000)

        onFinish()
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.rectivo_logo_sintexto),
            contentDescription = "Logo recTivo",
            modifier = Modifier
                .size(250.dp)
                .graphicsLayer(
                    alpha = alpha.value,
                    scaleX = scale.value,
                    scaleY = scale.value
                )
        )
        Text(
            text = "recTivo",
            fontSize = 70.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFFFB607),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 190.dp)
        )
    }
}