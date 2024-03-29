package nl.narvekar.abhishek.student649744.fragments

import android.graphics.Paint.Align
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemsIndexed
import coil.compose.AsyncImage
import kotlinx.coroutines.launch
import nl.narvekar.abhishek.student649744.R
import nl.narvekar.abhishek.student649744.data.Article
import nl.narvekar.abhishek.student649744.navigation.BottomBarNavigation
import nl.narvekar.abhishek.student649744.navigation.Routes
import nl.narvekar.abhishek.student649744.viewModel.ArticleViewModel
import nl.narvekar.abhishek.student649744.viewModel.LoginViewModel

@Composable
fun HomeScreen(
    navController: NavController,
    articleViewModel: ArticleViewModel,
    loginViewModel: LoginViewModel,
    isNetworkAvailable: Boolean
) {
    if (!isNetworkAvailable) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.padding(14.dp),
                text = stringResource(R.string.ui_no_internet_connection),
                fontSize = 20.sp,
                color = MaterialTheme.colors.onSurface
            )
            Text(
                modifier = Modifier.padding(14.dp),
                text = stringResource(R.string.ui_connect_to_wifi_or_mobile_data_message),
                fontSize = 15.sp,
                color = MaterialTheme.colors.onSurface
            )
        }
    }
    else {
        val articles = articleViewModel.articles.collectAsLazyPagingItems()
        Scaffold(topBar = {
            TopAppBarForArticles(
                navController = navController,
                title = stringResource(R.string.ui_home_screen_title),
                articleViewModel,
                loginViewModel
            )
        },
            bottomBar = {
                BottomBarNavigation(navController = navController)
            },
            content = { innerPadding ->
                LazyColumn(Modifier.padding(innerPadding)) {
                    itemsIndexed(articles) { item, article ->
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
                                Text(text = stringResource(R.string.ui_cannot_load_articles_from_api))
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
    title: String,
    articleViewModel: ArticleViewModel,
    loginViewModel: LoginViewModel
) {
    val articles = articleViewModel.articles.collectAsLazyPagingItems()
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    androidx.compose.material.TopAppBar(
        elevation = 4.dp,
        title = {
            Text(text = title)
        },
        backgroundColor = MaterialTheme.colors.primary,
        actions = {
            IconButton(onClick = {
               articles.refresh()
            }) {
                // refresh icon here
                Icon(Icons.Filled.Refresh, null)
            }
            IconButton(onClick = {
                coroutineScope.launch {
                    loginViewModel.signOut(navController)
                }
                //loginViewModel.logout(sharedPreferences, navController)
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
                        contentDescription = null
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
