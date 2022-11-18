package nl.narvekar.abhishek.student649744.fragments

import android.content.SharedPreferences
import android.nfc.tech.MifareUltralight.PAGE_SIZE
import android.service.autofill.OnClickAction
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.materialIcon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import androidx.paging.compose.itemsIndexed
import coil.compose.AsyncImage
import coil.compose.rememberImagePainter
import kotlinx.coroutines.flow.first
import nl.narvekar.abhishek.student649744.Constants.AUTH_TOKEN_KEY
import nl.narvekar.abhishek.student649744.R
import nl.narvekar.abhishek.student649744.data.Article
import nl.narvekar.abhishek.student649744.data.ArticleList
import nl.narvekar.abhishek.student649744.fragments.components.ProgressBarLoading
import nl.narvekar.abhishek.student649744.navigation.BottomBarNavigation
import nl.narvekar.abhishek.student649744.navigation.Routes
import nl.narvekar.abhishek.student649744.ui.theme.Student649744Theme
import nl.narvekar.abhishek.student649744.viewModel.ArticleDetailViewModel
import nl.narvekar.abhishek.student649744.viewModel.ArticleViewModel
import okhttp3.Route

@Composable
fun HomeScreen(
    navController: NavController,
    sharedPreferences: SharedPreferences,
    articles: List<Article>,
    viewModel: ArticleViewModel
) {
    Student649744Theme {
        val loading = viewModel.progressBar.value

        Scaffold(topBar = {
            TopAppBarForArticles(
                navController = navController,
                sharedPreferences = sharedPreferences,
                title = "News Articles"
            )
        },
            bottomBar = {
                BottomBarNavigation(navController = navController)
            },
            content = { innerPadding ->
                LazyColumn(Modifier.padding(innerPadding)) {
                    itemsIndexed(articles) { index, article ->
//                        viewModel.onChangeScrollPosition(index)
//                        if ((index + 1) >= (page * PAGE_SIZE) && !loading) {
//                            viewModel.nextPage()
//                        }
                        ArticleItem(article = article) {
                            navController.navigate(Routes.ArticleDetail.route + "/${it.Id}")
                        }
                    }
                }
                Row(Modifier.fillMaxWidth()) {
                    ProgressBarLoading(isLoading = loading)
                }
            }
        )
    }

}

@Composable
fun TopAppBarForArticles(
    navController: NavController,
    sharedPreferences: SharedPreferences,
    title: String
) {
    androidx.compose.material.TopAppBar(
        elevation = 4.dp,
        title = {
            Text(text = title)
        },
        backgroundColor = MaterialTheme.colors.primarySurface,
        navigationIcon = {

        }, actions = {
            IconButton(onClick = {
                //loadData(viewModel)
            }) {
                // refresh icon here
                Icon(Icons.Filled.Refresh, null)
            }
            IconButton(onClick = {
                logout(
                    sharedPreferences = sharedPreferences,
                    navController = navController
                )
            }) {
                Icon(Icons.Filled.ExitToApp, contentDescription = null)
            }
        })
}


@Composable
fun ArticleItem(
    article: Article,
    onClickAction: (Article) -> Unit,
    ) {
    Card(
        modifier = Modifier
            .padding(top = 16.dp, start = 16.dp, end = 16.dp)
            .fillMaxWidth()
            //.wrapContentHeight(align = Alignment.Top)
            .clickable { onClickAction(article) },
        elevation = 8.dp,
        backgroundColor = Color.White,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            ProfilePictureArticle(article, 70.dp)
            Text(text = article.Title, style = MaterialTheme.typography.h6, color = MaterialTheme.colors.primarySurface)
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
            error = painterResource(R.drawable.placeholder)
        )
    }
}


fun logout(
    sharedPreferences: SharedPreferences,
    navController: NavController
) {
    // clear authToken
    val editor = sharedPreferences.edit()
    editor.putString(AUTH_TOKEN_KEY, "")
    editor.clear()
    editor.apply()

    navController.navigate(Routes.Login.route) {
        popUpTo(Routes.Home.route) {
            inclusive = true
        }
    }
}
