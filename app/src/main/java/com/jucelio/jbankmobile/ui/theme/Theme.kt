package com.jucelio.jbankmobile.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val JBankPurple = Color(0xFF8B2CFF)
private val JBankPurpleLight = Color(0xFFB23CFF)
private val JBankGreen = Color(0xFF00C96B)
private val JBankDark = Color(0xFF050611)
private val JBankSurfaceDark = Color(0xFF101220)
private val JBankLightBackground = Color(0xFFF7F7FB)
private val JBankTextLight = Color(0xFFF8F5FF)

private val LightColors = lightColorScheme(
    primary = JBankPurple,
    onPrimary = Color.White,

    secondary = JBankGreen,
    onSecondary = Color.White,

    tertiary = JBankPurpleLight,

    background = JBankLightBackground,
    onBackground = Color(0xFF111322),

    surface = Color.White,
    onSurface = Color(0xFF111322),

    error = Color(0xFFEF4444)
)

private val DarkColors = darkColorScheme(
    primary = JBankPurpleLight,
    onPrimary = Color.White,

    secondary = JBankGreen,
    onSecondary = Color.Black,

    tertiary = JBankPurple,

    background = JBankDark,
    onBackground = JBankTextLight,

    surface = JBankSurfaceDark,
    onSurface = JBankTextLight,

    error = Color(0xFFFF6478)
)

@Composable
fun JBankTheme(
    darkTheme: Boolean = true,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) {
            DarkColors
        } else {
            LightColors
        },
        content = content
    )
}