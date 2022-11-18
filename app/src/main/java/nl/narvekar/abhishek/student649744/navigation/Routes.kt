package nl.narvekar.abhishek.student649744.navigation


sealed class Routes(val route: String) {
    object Login: Routes(route = "login")
    object Home: Routes(route = "home")
    object Register: Routes(route = "register")
    object ArticleDetail: Routes(route = "articledetail")
    object Favorites: Routes(route = "favorites")

    companion object {
        fun getDestination() = Login.route
    }
}