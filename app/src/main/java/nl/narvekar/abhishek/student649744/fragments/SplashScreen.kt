package nl.narvekar.abhishek.student649744.fragments

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import nl.narvekar.abhishek.student649744.R
import nl.narvekar.abhishek.student649744.utils.Session
import nl.narvekar.abhishek.student649744.navigation.Routes


@Composable
fun SplashScreen(navController: NavController) {

    val isUserLoggedIn = Session.isUserLoggedIn()

    val scale = remember {
        Animatable(0f)
    }
    LaunchedEffect(key1 = true) {
        scale.animateTo(
            targetValue = 0.3f,
            animationSpec = tween(
                durationMillis = 500,
                easing = {
                    OvershootInterpolator(2f).getInterpolation(it)
                }
            )
        )
        // display the duration amount for splash screen
        delay(3000L)
        if (!isUserLoggedIn) {
            navController.navigate(Routes.Login.route)
        }
        else {
            navController.navigate(Routes.Home.route)
        }
    }
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Image(painter = painterResource(R.drawable.splash_screen_logo), contentDescription = "logo")
    }
}