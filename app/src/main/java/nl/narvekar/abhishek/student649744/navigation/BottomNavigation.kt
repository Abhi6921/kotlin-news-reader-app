package nl.narvekar.abhishek.student649744.navigation

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState


@Composable
fun BottomBarNavigation(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    if (currentRoute == null || currentRoute == Routes.Login.route) {
        return
    }

    BottomNavigation {

        val homeSelected = currentRoute == Routes.Home.route
        BottomNavigationItem(
            icon = {
                Icon(
                    imageVector = if (homeSelected) Icons.Filled.Home else Icons.Default.Home,
                    contentDescription = Routes.Home.route
                )
            },
            selected = homeSelected,
            onClick = {
                if (!homeSelected) {
                    navController.navigate(Routes.Home.route) {
                        popUpTo(Routes.Home.route) { inclusive = true }
                    }
                }
            },
            label = { Text(Routes.Home.route) }
        )

        val searchSelected = currentRoute == Routes.Favorites.route
        BottomNavigationItem(
            icon = {
                Icon(
                    imageVector = if (searchSelected) Icons.Filled.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = Routes.Favorites.route
                )
            },
            selected = searchSelected,
            onClick = {
                if (!searchSelected) {
                    navController.navigate(Routes.Favorites.route)
                }
            },
            label = { Text(Routes.Favorites.route) }
        )
    }
}