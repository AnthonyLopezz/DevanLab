package com.devan.lab.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = Mischka500,
    secondary = Mischka700,
    tertiary = Mischka900,
    background = Mischka950,
    surface = Mischka800,
    onPrimary = Mischka50,
    onSecondary = Mischka200,
    onTertiary = Mischka300,
    onBackground = Mischka100,
    onSurface = Mischka100,
)

private val LightColorScheme = lightColorScheme(
    primary = Mischka500,
    secondary = Mischka300,
    tertiary = Mischka200,
    background = Mischka50,
    surface = Mischka100,
    onPrimary = Mischka950,
    onSecondary = Mischka900,
    onTertiary = Mischka800,
    onBackground = Mischka700,
    onSurface = Mischka700,
)

@Composable
fun LabTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit,
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content,
    )
}