package com.seasonalrice.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColors = lightColorScheme(
    primary = Color(0xFF5E8B65),
    onPrimary = Color.White,
    secondary = Color(0xFFD9A35F),
    background = Color(0xFFFFFCF6),
    surface = Color.White,
    onBackground = Color(0xFF223126),
    onSurface = Color(0xFF223126)
)

private val DarkColors = darkColorScheme(
    primary = Color(0xFF8DC795),
    secondary = Color(0xFFF3C98D),
    background = Color(0xFF172018),
    surface = Color(0xFF243127),
    onBackground = Color(0xFFF0F5EE),
    onSurface = Color(0xFFF0F5EE)
)

@Composable
fun SeasonalRiceTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = if (androidx.compose.foundation.isSystemInDarkTheme()) DarkColors else LightColors,
        typography = Typography,
        content = content
    )
}
