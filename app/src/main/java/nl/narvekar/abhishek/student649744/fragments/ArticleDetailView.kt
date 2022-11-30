package nl.narvekar.abhishek.student649744.fragments

import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavController
import coil.compose.AsyncImage
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
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
    favoritesViewModel: FavoritesViewModel,
   articleDetailViewModel: ArticleDetailViewModel
) {
    val scrollState = rememberScrollState()
    val article: Article? = articleDetailViewModel.getArticleById(detailId).results.firstOrNull()
    val uriHandler = LocalUriHandler.current
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                elevation = 4.dp,
                title = {
                    Text(text = stringResource(R.string.ui_topbar_newsdetail_title))
                },
                backgroundColor = MaterialTheme.colors.primary,
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = null)
                    }
                },
                actions = {
                    IconButton(onClick = {
                            val share = Intent.createChooser(Intent().apply {
                                action = Intent.ACTION_SEND
                                putExtra(Intent.EXTRA_TEXT, article?.Url)
                                putExtra(Intent.EXTRA_TITLE, article?.Title)
                                type = "message/rfc822"
                            }, null)

                        startActivity(context, Intent.createChooser(share, null), null)
                    }) {
                        Icon(Icons.Default.Share, contentDescription = null)
                    }
                }
            )
        },
        content = {
            Column(modifier = Modifier
                .verticalScroll(state = scrollState)
                .fillMaxSize()) {
                if (article != null) {
                    AsyncImage(
                        model = article.Image,
                        contentDescription = stringResource(R.string.ui_article_image),
                        modifier = Modifier.size(500.dp),
                        contentScale = ContentScale.Crop,
                        placeholder =  painterResource(R.drawable.placeholder)
                    )
                    Text(
                        text = article.Title,
                        modifier = Modifier.padding(4.dp),
                        style = MaterialTheme.typography.h6,
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Bold
                    )

                    FavoriteButton(favoritesViewModel, article)

                    Divider(modifier = Modifier.padding(bottom = 4.dp))
                    Text(
                        text = article.Summary,
                        modifier = Modifier.padding(4.dp),
                        style = MaterialTheme.typography.body1,
                        fontFamily = FontFamily.SansSerif,
                        fontWeight = FontWeight.Bold
                    )

                    Divider(modifier = Modifier.padding(bottom = 4.dp))
                    //val text = "open in browser"
                    ClickableText(
                        text = AnnotatedString(stringResource(R.string.ui_open_in_borwser_text)),
                        style = TextStyle(
                            color = MaterialTheme.colors.onSurface,
                            fontSize = 16.sp
                        ),
                        onClick = {
                            uriHandler.openUri(article.Url)
                        },
                    )
                    Divider(modifier = Modifier.padding(bottom = 4.dp))

                    var articleDate = LocalDateTime.parse(article.PublishDate)
                    //var formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm a", Locale.GERMANY)
                    //var formatter = DateTimeFormatter.ofPattern(stringResource(R.string.ui_date_time_format), Locale.GERMANY)
                    var formatter = DateTimeFormatter.ofPattern("dd-MMMM-yyyy HH:mm a", Locale.GERMANY)
                    var formattedArticleDate = articleDate.format(formatter)

                    Text(
                        text = "Published on: $formattedArticleDate",
                        modifier = Modifier.padding(4.dp),
                        style = MaterialTheme.typography.body1,
                        fontFamily = FontFamily.Monospace
                    )
                    Divider(modifier = Modifier.padding(bottom = 4.dp))

                    if (article.Related.isNotEmpty()) {
                        Text(
                            text = "Related Articles",
                            modifier = Modifier.padding(4.dp),
                            style = MaterialTheme.typography.h6,
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.SemiBold
                        )
                        var count: Int = 0
                        for (articleRelated in article.Related) {
                            count += 1
                            ClickableText(
                                text = AnnotatedString("view article $count"),
                                modifier = Modifier.padding(4.dp),
                                style = TextStyle(
                                    color = MaterialTheme.colors.onSurface,
                                    fontSize = 16.sp
                                ),
                                onClick = {
                                    uriHandler.openUri(articleRelated)
                                }
                            )
                        }
                    }
                    Divider(modifier = Modifier.padding(bottom = 4.dp))
                    Text(
                        text = "Categories",
                        style = MaterialTheme.typography.h6,
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.SemiBold
                    )
                    for (category in article.Categories) {
                        Row(horizontalArrangement = Arrangement.SpaceEvenly) {
                            Button(
                                onClick = { /*TODO*/ },
                                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary)
                            ) {
                                Text(
                                    text = category.Name,
                                    style = TextStyle(fontSize = 15.sp)
                                )
                            }
                        }
                    }
                }
                else {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = stringResource(R.string.ui_no_article_description),
                            fontFamily = FontFamily.SansSerif,
                            fontWeight = FontWeight.Bold
                        )
                    }

                }
            }
        }
    )
}

@Composable
fun FavoriteButton(
    favoritesViewModel: FavoritesViewModel,
    article: Article
) {
    var isFavorite by remember { mutableStateOf(article.IsLiked) }

    val context = LocalContext.current

    IconButton(
        onClick = {
            isFavorite = !isFavorite
            if (isFavorite) {
                favoritesViewModel.likeArticle(article.Id)
                Toast.makeText(context, context.getString(R.string.saved_to_favorites_message), Toast.LENGTH_SHORT).show()
            } else {
                favoritesViewModel.removeArticle(article.Id)
                Toast.makeText(context, context.getString(R.string.removed_from_favorites), Toast.LENGTH_SHORT).show()
            }
        }
    ) {
        Icon(imageVector = if (isFavorite) { Icons.Filled.Favorite } else { Icons.Filled.FavoriteBorder }, contentDescription = "favorite icon")
    }
}

@Preview(showBackground = true)
@Composable
fun HorizontalButtons() {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly

    ) {
        Button(onClick = { /*TODO*/ }) {
            Text(
                text = "Category 1",
                style = TextStyle(fontSize = 15.sp)
            )
        }
        Spacer(modifier = Modifier.width(14.dp))
        Button(onClick = { /*TODO*/ }) {
            Text(
                text = "Category 2",
                style = TextStyle(fontSize = 15.sp)
            )
        }
        Spacer(modifier = Modifier.width(15.dp))
        Button(onClick = { /*TODO*/ }) {
            Text(
                text = "Category 4",
                style = TextStyle(fontSize = 15.sp)
            )
        }
    }
}

