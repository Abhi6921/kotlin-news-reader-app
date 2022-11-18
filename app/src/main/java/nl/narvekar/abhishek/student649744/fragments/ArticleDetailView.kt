package nl.narvekar.abhishek.student649744.fragments

import android.content.SharedPreferences
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import nl.narvekar.abhishek.student649744.Constants.AUTH_TOKEN_KEY
import nl.narvekar.abhishek.student649744.R
import nl.narvekar.abhishek.student649744.data.Article
import nl.narvekar.abhishek.student649744.viewModel.ArticleDetailViewModel
import nl.narvekar.abhishek.student649744.viewModel.ArticleViewModel
import nl.narvekar.abhishek.student649744.viewModel.FavoritesViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ArticleDetailScreen(
    navController: NavController,
    detailId: Int,
    sharedPreferences: SharedPreferences,
    favoritesViewModel: FavoritesViewModel,
   articleDetailViewModel: ArticleDetailViewModel
) {

    val scrollState = rememberScrollState()

    val authToken = sharedPreferences.getString(AUTH_TOKEN_KEY, "").toString()

    val article: Article? = articleDetailViewModel.getArticleById(authToken, detailId).results.firstOrNull()

    val uriHandler = LocalUriHandler.current
    Column(
        modifier = Modifier
            .verticalScroll(state = scrollState)
    ) {
        TopAppBar(
                elevation = 4.dp,
                title = {
                    Text(text = "News Detail Screen")
                },
                backgroundColor = MaterialTheme.colors.primarySurface,
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = null)
                    }
                }, actions = {

                })

        if (article != null) {

            AsyncImage(
                model = article.Image,
                contentDescription = "Article Image",
                modifier = Modifier.size(500.dp),
                contentScale = ContentScale.Crop,
                error =  painterResource(R.drawable.placeholder)
            )
            Text(
                text = article.Title,
                modifier = Modifier.padding(4.dp),
                style = MaterialTheme.typography.h6,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Bold
            )
            
            FavoriteButton(favoritesViewModel, sharedPreferences, article)

            Divider(modifier = Modifier.padding(bottom = 4.dp))
            Text(
                text = article.Summary,
                modifier = Modifier.padding(4.dp),
                style = MaterialTheme.typography.body1,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Bold
            )

            Divider(modifier = Modifier.padding(bottom = 4.dp))
            //Text(text = "Full article at nos.nl")
            val text = "open in browser"
            ClickableText(
                text = AnnotatedString(text),
                style = TextStyle(
                    color = MaterialTheme.colors.primary,
                    fontSize = 16.sp
                ),
                onClick = {
                    uriHandler.openUri(article.Url)
                },
            )
            Divider(modifier = Modifier.padding(bottom = 4.dp))

            var articleDate = LocalDateTime.parse(article.PublishDate)
            var formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm a", Locale.GERMANY)
            var formattedArticleDate = articleDate.format(formatter)

            Text(
                text = "Published on: $formattedArticleDate",
                modifier = Modifier.padding(4.dp),
                style = MaterialTheme.typography.body1,
                fontFamily = FontFamily.Monospace
            )
        }
    }
}

@Composable
fun FavoriteButton(
    favoritesViewModel: FavoritesViewModel,
    sharedPreferences: SharedPreferences,
    article: Article
) {
    var isFavorite by remember { mutableStateOf(article.IsLiked) }

    val authToken = sharedPreferences.getString(AUTH_TOKEN_KEY, "").toString()
    Log.d("Token articleDetail", "authToken: $authToken")

    val context = LocalContext.current

    IconButton(
        onClick = {
            isFavorite = !isFavorite
            if (isFavorite) {
                favoritesViewModel.likeArticle(authToken, article.Id)
                Toast.makeText(context, "Saved successfully", Toast.LENGTH_SHORT).show()
            } else {
                favoritesViewModel.removeArticle(authToken, article.Id)
                Toast.makeText(context, "Deleted successfully", Toast.LENGTH_SHORT).show()
            }
        }
    ) {
        Icon(imageVector = if (isFavorite) { Icons.Filled.Favorite } else { Icons.Filled.FavoriteBorder }, contentDescription = "favorite icon")
    }
}

