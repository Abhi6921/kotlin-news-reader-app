package nl.narvekar.abhishek.student649744

import android.content.ContentValues.TAG
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import nl.narvekar.abhishek.student649744.Constants.AUTH_TOKEN_KEY
import nl.narvekar.abhishek.student649744.Constants.PREF_KEY
import nl.narvekar.abhishek.student649744.navigation.NavigationScreen
import nl.narvekar.abhishek.student649744.ui.theme.Student649744Theme
import nl.narvekar.abhishek.student649744.viewModel.*

class MainActivity : ComponentActivity() {
    // this commit is from the security branch
    lateinit var sharedPreferences: SharedPreferences
    val favoritesViewModel by viewModels<FavoritesViewModel>()
    val articleDetailViewModel by viewModels<ArticleDetailViewModel>()
    val loginViewModel by viewModels<LoginViewModel>()
    val registerViewModel by viewModels<RegisterViewModel>()
    //val articleViewModel by viewModels<ArticleViewModel>()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        Session.init(this)
        super.onCreate(savedInstanceState)

        setContent {
            Student649744Theme {
                sharedPreferences = getSharedPreferences(PREF_KEY, Context.MODE_PRIVATE)
                val authToken = sharedPreferences.getString(AUTH_TOKEN_KEY, "").toString()

                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    val articleViewModel = ArticleViewModel(authToken)
                    NavigationScreen(
                        sharedPreferences,
                        articleViewModel,
                        favoritesViewModel,
                        authToken,
                        articleDetailViewModel,
                        loginViewModel,
                        registerViewModel
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Student649744Theme {
    }
}