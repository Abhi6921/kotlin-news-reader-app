package nl.narvekar.abhishek.student649744.fragments

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import nl.narvekar.abhishek.student649744.R
import nl.narvekar.abhishek.student649744.data.Article
import nl.narvekar.abhishek.student649744.data.ArticleList
import nl.narvekar.abhishek.student649744.navigation.BottomBarNavigation
import nl.narvekar.abhishek.student649744.navigation.Routes
import nl.narvekar.abhishek.student649744.viewModel.ArticleViewModel
import nl.narvekar.abhishek.student649744.viewModel.FavoritesViewModel
import nl.narvekar.abhishek.student649744.viewModel.LoginViewModel

@Composable
fun FavoritesScreen(
    navController: NavController,
    favoritesViewModel: FavoritesViewModel = viewModel()
) {
    favoritesViewModel.getFavoriteArticles()
    var isLoading = favoritesViewModel.isLoading.value
    val coroutineScope = rememberCoroutineScope()

    val favoriteArticles by favoritesViewModel.mutableFavoriteArticleState.collectAsState()


    Scaffold(topBar = {
        TopAppBar(
            elevation = 4.dp,
            title = {
                Text(stringResource(R.string.ui_topbar_favorites_title))
            },
            backgroundColor = MaterialTheme.colors.primary
        )
    },
        bottomBar = {
            BottomBarNavigation(navController = navController)
        },
        content = { innerPadding ->
            favoriteArticles?.let { articles ->
                if(articles.results.isNotEmpty()) {
                    LazyColumn(Modifier.padding(innerPadding)) {
                        items(articles.results) { article ->
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
                if (articles.results.isEmpty()) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = stringResource(R.string.ui_no_favorite_articles))
                    }
                }
            }


        }
    )
}

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