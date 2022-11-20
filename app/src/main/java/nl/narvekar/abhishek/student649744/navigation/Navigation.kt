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
import nl.narvekar.abhishek.student649744.data.Article
import nl.narvekar.abhishek.student649744.fragments.*
import nl.narvekar.abhishek.student649744.viewModel.*

const val ARTICLE_ID_KEY = "Id"

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavigationScreen(
    sharedPreferences: SharedPreferences,
    viewModel: ArticleViewModel,
    favoritesViewModel: FavoritesViewModel,
    authToken: String,
    articleDetailViewModel: ArticleDetailViewModel,
    loginViewModel: LoginViewModel,
    registerViewModel: RegisterViewModel
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = if (authToken.isEmpty()) { Routes.getDestination() } else { Routes.Home.route }
    ) {
        composable(route = Routes.Login.route) {
            LoginScreen(
                navController = navController,
                sharedPreferences = sharedPreferences,
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
                sharedPreferences,
                viewModel,
                loginViewModel
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
                sharedPreferences,
                favoritesViewModel,
                articleDetailViewModel
            )
        }

        composable(route = Routes.Favorites.route) {
            FavoritesScreen(
                navController,
                sharedPreferences,
                favoritesViewModel.favoritesListResponse.results,
                viewModel,
                favoritesViewModel,
                loginViewModel
            )
        }
    }
}
