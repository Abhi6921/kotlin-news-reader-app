package nl.narvekar.abhishek.student649744.fragments

import android.content.SharedPreferences
import android.service.autofill.OnClickAction
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import kotlinx.coroutines.launch
import nl.narvekar.abhishek.student649744.Constants.AUTH_TOKEN_KEY
import nl.narvekar.abhishek.student649744.R
import nl.narvekar.abhishek.student649744.Session
import nl.narvekar.abhishek.student649744.data.Article
import nl.narvekar.abhishek.student649744.fragments.components.ProgressBarLoading
import nl.narvekar.abhishek.student649744.navigation.BottomBarNavigation
import nl.narvekar.abhishek.student649744.navigation.Routes
import nl.narvekar.abhishek.student649744.viewModel.ArticleViewModel
import nl.narvekar.abhishek.student649744.viewModel.FavoritesViewModel
import nl.narvekar.abhishek.student649744.viewModel.LoginViewModel
import kotlin.math.log

@Composable
fun FavoritesScreen(
    navController: NavController,
    articles: List<Article>,
    articleViewModel: ArticleViewModel,
    favoritesViewModel: FavoritesViewModel,
    loginViewModel: LoginViewModel
) {

    favoritesViewModel.getFavoriteArticles()
    var isLoading = favoritesViewModel.isLoading.value
    val coroutineScope = rememberCoroutineScope()

    Scaffold(topBar = {
        TopAppBar(
            elevation = 4.dp,
            title = {
                Text(stringResource(R.string.ui_topbar_favorites_title))
            },
            backgroundColor = MaterialTheme.colors.primary,
            actions = {
                IconButton(
                    onClick = {
                        coroutineScope.launch {
                            loginViewModel.signOut(navController)
                        }
                    }
                ) {
                    Icon(Icons.Filled.ExitToApp, null)
                }
            })
    },
        bottomBar = {
            BottomBarNavigation(navController = navController)
        },
        content = { innerPadding ->
            if(articles.isNotEmpty()) {
                LazyColumn(Modifier.padding(innerPadding)) {
                    items(articles) { article ->
                        FavoritesArticleItem(article = article, isLiked = article.IsLiked) {
                            navController.navigate(Routes.ArticleDetail.route + "/${it.Id}")
                        }
                    }
                }
            }
            else {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                        CircularProgressIndicator(color = MaterialTheme.colors.onSurface)
                }
            }
        }

    )
}

//https://www.nu.nl/eindhoven/6050288/man-bijt-agent-in-been-na-aanhouding-in-woning-in-gemert.html
//@Preview(showBackground = true)
@Composable
fun FavoritesArticleItem(
    article: Article,
    isLiked: Boolean,
    onClickAction: (Article) -> Unit
) {
    Card(
        modifier = Modifier
            .padding(top = 8.dp, start = 8.dp, end = 8.dp)
            .fillMaxWidth()
            .clickable { onClickAction(article) },
        elevation = 8.dp,
        backgroundColor = MaterialTheme.colors.surface
    ) {
        Row(modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            ProfilePictureArticle(article = article, profilePicSize = 70.dp)
            Text(
                text = article.Title,
                style = MaterialTheme.typography.h6,
                color = MaterialTheme.colors.onSurface
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
                    contentDescription = null
                )
            }
        }

    }

}