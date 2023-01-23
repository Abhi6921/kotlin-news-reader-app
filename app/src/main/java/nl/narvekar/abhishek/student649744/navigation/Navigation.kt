package nl.narvekar.abhishek.student649744.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import nl.narvekar.abhishek.student649744.utils.Session
import nl.narvekar.abhishek.student649744.fragments.*
import nl.narvekar.abhishek.student649744.viewModel.*

const val ARTICLE_ID_KEY = "Id"

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavigationScreen(
    isNetworkAvailable: Boolean
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination =  Routes.getDestination()
    ) {
        composable(route = Routes.Splash.route) {
            SplashScreen(navController = navController)
        }
        composable(route = Routes.Login.route) {
            LoginScreen(
                navController = navController
            )
        }
        composable(route = Routes.Register.route) {
            RegisterScreen(
                navController = navController
            )
        }
        composable(route = Routes.Home.route) {
            HomeScreen(
                navController,
                isNetworkAvailable
            )
        }
        composable(
            route = Routes.ArticleDetail.route + "/{$ARTICLE_ID_KEY}",
            arguments = listOf(navArgument(ARTICLE_ID_KEY) {
                type = NavType.IntType
            })
        ) { navBackStackEntry ->
            ArticleDetailScreen(
                navController,
                navBackStackEntry.arguments!!.getInt(ARTICLE_ID_KEY)
            )
        }

        composable(route = Routes.Favorites.route) {
            FavoritesScreen(
                navController
            )
        }
    }
}
