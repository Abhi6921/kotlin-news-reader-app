package nl.narvekar.abhishek.student649744.fragments

import android.content.SharedPreferences
import android.service.autofill.OnClickAction
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import nl.narvekar.abhishek.student649744.Constants.AUTH_TOKEN_KEY
import nl.narvekar.abhishek.student649744.R
import nl.narvekar.abhishek.student649744.data.Article
import nl.narvekar.abhishek.student649744.navigation.BottomBarNavigation
import nl.narvekar.abhishek.student649744.navigation.Routes
import nl.narvekar.abhishek.student649744.viewModel.ArticleViewModel
import nl.narvekar.abhishek.student649744.viewModel.FavoritesViewModel
import nl.narvekar.abhishek.student649744.viewModel.LoginViewModel
import kotlin.math.log

@Composable
fun FavoritesScreen(
    navController: NavController,
    sharedPreferences: SharedPreferences,
    articles: List<Article>,
    articleViewModel: ArticleViewModel,
    favoritesViewModel: FavoritesViewModel,
    loginViewModel: LoginViewModel
) {
    val authToken = sharedPreferences.getString(AUTH_TOKEN_KEY, "").toString()
    favoritesViewModel.getFavoriteArticles(authToken)

    Scaffold(topBar = {
        TopAppBarForArticles(
            navController = navController,
            sharedPreferences = sharedPreferences,
            title = "Favorites",
            articleViewModel,
            loginViewModel
        )
    },
        bottomBar = {
            BottomBarNavigation(navController = navController)
        },
        content = { innerPadding ->
            if (articles.isEmpty()) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "You have no favorite articles")
                }
            }
            else {
                LazyColumn(Modifier.padding(innerPadding)) {
                    items(articles) { article ->
                        FavoritesArticleItem(article = article) {
                            navController.navigate(Routes.ArticleDetail.route + "/${it.Id}")
                        }
                    }
                }
            }
        }

    )
}

//@Preview(showBackground = true)
@Composable
fun FavoritesArticleItem(
    article: Article,
    onClickAction: (Article) -> Unit
) {
    Card(
        modifier = Modifier
            .padding(top = 8.dp, start = 8.dp, end = 8.dp)
            .fillMaxWidth()
            .clickable { onClickAction(article) },
        elevation = 8.dp,
        backgroundColor = MaterialTheme.colors.primarySurface
    ) {
        Row(modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            ProfilePictureArticle(article = article, profilePicSize = 70.dp)
            Text(
                text = article.Title,
                style = MaterialTheme.typography.h6,
                color = MaterialTheme.colors.onPrimary
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.End

        ) {
            Column(horizontalAlignment = Alignment.End) {
                //Spacer(modifier = Modifier.height(25.dp))
                Icon(
                    imageVector = Icons.Filled.Favorite,
                    contentDescription = "favorite icon"
                )
            }

        }

    }

}