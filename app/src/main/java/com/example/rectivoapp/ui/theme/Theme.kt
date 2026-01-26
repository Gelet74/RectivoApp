package com.rectivo.ui.theme

import androidx.compose.material3.*
import androidx.compose.runtime.Composable

private val LightColors = lightColorScheme(
    primary = RectivoPrimary,
    onPrimary = RectivoOnPrimary,
    primaryContainer = RectivoPrimaryLight,
    onPrimaryContainer = RectivoOnPrimary,

    secondary = RectivoSecondary,
    onSecondary = RectivoOnSecondary,

    background = RectivoBackgroundDark,
    onBackground = RectivoOnBackground,

    surface = RectivoSurface,
    onSurface = RectivoOnSurface,
)

@Composable
fun RectivoTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColors,
        typography = Typography(),
        shapes = Shapes(),
        content = content
    )
}
