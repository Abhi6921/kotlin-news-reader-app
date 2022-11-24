package nl.narvekar.abhishek.student649744.ui.theme

import android.annotation.SuppressLint
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val DarkColorPalette = darkColors(
        primary = purple,
        primaryVariant = tealDark,
        secondary = redDark,
        background = Color.Black,
        surface = Color.DarkGray,
        onPrimary = Color.White,


)

private val LightColorPalette = lightColors(
        primary = teal700,
        primaryVariant = tealDark,
        secondary = red100,
        background = Color.White,
        surface = Color.White,
        onPrimary = Color.White,
)



@Composable
fun Student649744Theme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val systemUiController = rememberSystemUiController()

    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    if (darkTheme) {
        systemUiController.setSystemBarsColor(color = MaterialTheme.colors.primary)
    }

    MaterialTheme(
            colors = colors,
            typography = Typography,
            shapes = Shapes,
            content = content
    )
}