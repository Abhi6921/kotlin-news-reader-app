package nl.narvekar.abhishek.student649744.fragments

import android.content.ContentValues.TAG
import android.content.SharedPreferences
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.compose.AsyncImage
import kotlinx.coroutines.delay
import nl.narvekar.abhishek.student649744.Constants
import nl.narvekar.abhishek.student649744.Constants.AUTH_TOKEN_KEY
import nl.narvekar.abhishek.student649744.R
import nl.narvekar.abhishek.student649744.data.Article
import nl.narvekar.abhishek.student649744.navigation.BottomBarNavigation
import nl.narvekar.abhishek.student649744.navigation.Routes
import nl.narvekar.abhishek.student649744.ui.theme.Student649744Theme
import nl.narvekar.abhishek.student649744.viewModel.ArticlePager
import nl.narvekar.abhishek.student649744.viewModel.ArticleViewModel
import nl.narvekar.abhishek.student649744.viewModel.LoginViewModel

@Composable
fun HomeScreen(
    navController: NavController,
    sharedPreferences: SharedPreferences,
    articleViewModel: ArticleViewModel,
    loginViewModel: LoginViewModel
) {
    Student649744Theme {
        LaunchedEffect(key1 = Unit) {
            delay(6000)
        }

        val articles = articleViewModel.articles.collectAsLazyPagingItems()
        Scaffold(topBar = {
            TopAppBarForArticles(
                navController = navController,
                sharedPreferences = sharedPreferences,
                title = "News Articles",
                articleViewModel,
                loginViewModel
            )
        },
            bottomBar = {
                BottomBarNavigation(navController = navController)
            },
            content = { innerPadding ->
                LazyColumn(Modifier.padding(innerPadding)) {
                    items(articles) { article ->
                        if (article != null) {
                            ArticleItem(article = article, isLiked = article.IsLiked) {
                                navController.navigate(Routes.ArticleDetail.route + "/${it.Id}")
                            }

                        }
                    }

                }
                
                articles.apply {
                    when {
                        loadState.refresh is LoadState.Loading -> {
                            Row(Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    CircularProgressIndicator(color = MaterialTheme.colors.onSurface)
                                }
                            }
                        }
                        loadState.append is LoadState.Loading -> {
                            Row(
                                Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    CircularProgressIndicator(color = MaterialTheme.colors.onSurface)
                                }
                            }
                        }
                        loadState.append is LoadState.Error -> {
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(text = "error loading from api")
                            }
                        }
                    }
                }
            }
        )
    }

}

@Composable
fun TopAppBarForArticles(
    navController: NavController,
    sharedPreferences: SharedPreferences,
    title: String,
    articleViewModel: ArticleViewModel,
    loginViewModel: LoginViewModel
) {
    val articles = articleViewModel.articles.collectAsLazyPagingItems()
    androidx.compose.material.TopAppBar(
        elevation = 4.dp,
        title = {
            Text(text = title)
        },
        backgroundColor = MaterialTheme.colors.primary,
        navigationIcon = {

        }, actions = {
            IconButton(onClick = {
               articles.refresh()
            }) {
                // refresh icon here
                Icon(Icons.Filled.Refresh, null)
            }
            IconButton(onClick = {
                loginViewModel.logout(sharedPreferences, navController)
            }) {
                Icon(Icons.Filled.ExitToApp, contentDescription = null)
            }
        })
}


@Composable
fun ArticleItem(
   article: Article,
   isLiked: Boolean,
   onClickAction: (Article) -> Unit
   ) {
    Card(
        modifier = Modifier
            .padding(top = 16.dp, start = 16.dp, end = 16.dp)
            .fillMaxWidth()
            //.wrapContentHeight(align = Alignment.Top)
            .clickable { onClickAction(article) },
        elevation = 8.dp,
        backgroundColor = MaterialTheme.colors.surface,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            ProfilePictureArticle(article, 70.dp)
            Text(
                text = article.Title,
                style = MaterialTheme.typography.h6,
                color = MaterialTheme.colors.onSurface
            )
        }
        if (isLiked) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Column() {
                    Icon(
                        imageVector = Icons.Filled.Favorite,
                        contentDescription = "favorite icon"
                    )
                }
            }
        }
    }
}


@Composable
fun ProfilePictureArticle(article: Article, profilePicSize: Dp) {
    Card(
        shape = RoundedCornerShape(4.dp),
        modifier = Modifier.padding(16.dp),
        elevation = 4.dp
    ) {
        AsyncImage(
            model = article.Image,
            contentDescription = "",
            modifier = Modifier.size(profilePicSize),
            contentScale = ContentScale.Crop,
            placeholder = painterResource(R.drawable.placeholder)
        )
    }
}


//fun logout(
//    sharedPreferences: SharedPreferences,
//    navController: NavController
//) {
//    // clear authToken
//    val editor = sharedPreferences.edit()
//    editor.putString(AUTH_TOKEN_KEY, "")
//    editor.clear()
//    editor.apply()
//
//    navController.navigate(Routes.Login.route) {
//        popUpTo(Routes.Home.route) {
//            inclusive = true
//        }
//    }
//}
