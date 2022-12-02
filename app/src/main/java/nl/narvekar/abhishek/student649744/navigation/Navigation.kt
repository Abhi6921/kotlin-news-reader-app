package nl.narvekar.abhishek.student649744.navigation

import android.content.SharedPreferences
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import nl.narvekar.abhishek.student649744.Session
import nl.narvekar.abhishek.student649744.data.Article
import nl.narvekar.abhishek.student649744.fragments.*
import nl.narvekar.abhishek.student649744.viewModel.*

const val ARTICLE_ID_KEY = "Id"

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavigationScreen(
    articleViewModel: ArticleViewModel,
    favoritesViewModel: FavoritesViewModel,
    articleDetailViewModel: ArticleDetailViewModel,
    loginViewModel: LoginViewModel,
    registerViewModel: RegisterViewModel,
    isNetworkAvailable: Boolean
) {
    val navController = rememberNavController()
    val AuthToken = Session.getAuthToken()
    NavHost(
        navController = navController,
        startDestination =  Routes.getDestination()
    ) {
        composable(route = Routes.Splash.route) {
            SplashScreen(navController = navController)
        }
        composable(route = Routes.Login.route) {
            LoginScreen(
                navController = navController,
                loginViewModel = loginViewModel
            )
        }
        composable(route = Routes.Register.route) {
            RegisterScreen(
                navController = navController,
                registerViewModel = registerViewModel
            )
        }
        composable(route = Routes.Home.route) {
            HomeScreen(
                navController,
                articleViewModel,
                loginViewModel,
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
                navBackStackEntry.arguments!!.getInt(ARTICLE_ID_KEY),
                favoritesViewModel,
                articleDetailViewModel
            )
        }

        composable(route = Routes.Favorites.route) {
            FavoritesScreen(
                navController,
                favoritesViewModel.favoritesListResponse.results,
                articleViewModel,
                favoritesViewModel,
                loginViewModel
            )
        }
    }
}
