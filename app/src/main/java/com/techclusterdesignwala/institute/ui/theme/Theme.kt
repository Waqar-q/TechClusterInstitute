package com.techclusterdesignwala.institute.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColorScheme = lightColorScheme(
    primary = Navy800,
    onPrimary = Color.White,
    primaryContainer = Navy100,
    onPrimaryContainer = Navy900,
    secondary = Teal700,
    onSecondary = Color.White,
    secondaryContainer = Teal100,
    onSecondaryContainer = Color(0xFF002020),
    tertiary = Gold700,
    onTertiary = Color.White,
    tertiaryContainer = Gold100,
    onTertiaryContainer = Color(0xFF261900),
    background = BackgroundLight,
    onBackground = Charcoal900,
    surface = SurfaceLight,
    onSurface = Charcoal900,
    surfaceVariant = Navy50,
    onSurfaceVariant = Charcoal700,
    error = StatusAbsent,
    onError = Color.White,
    outline = Navy200
)

private val DarkColorScheme = darkColorScheme(
    primary = Navy300,
    onPrimary = Navy900,
    primaryContainer = Navy700,
    onPrimaryContainer = Navy100,
    secondary = Teal300,
    onSecondary = Color(0xFF003733),
    secondaryContainer = Teal700,
    onSecondaryContainer = Teal100,
    tertiary = Gold300,
    onTertiary = Color(0xFF3E2D00),
    tertiaryContainer = Color(0xFF5C4300),
    onTertiaryContainer = Gold100,
    background = BackgroundDark,
    onBackground = Color(0xFFE4E2E6),
    surface = SurfaceDark,
    onSurface = Color(0xFFE4E2E6),
    surfaceVariant = Color(0xFF45464F),
    onSurfaceVariant = Color(0xFFC5C6D0),
    error = Color(0xFFFFB4AB),
    onError = Color(0xFF690005),
    outline = Color(0xFF8F9099)
)

@Composable
fun TechClusterTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}