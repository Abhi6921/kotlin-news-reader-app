package nl.narvekar.abhishek.student649744

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import nl.narvekar.abhishek.student649744.connectionManager.NetworkConnectionLiveData
import nl.narvekar.abhishek.student649744.navigation.NavigationScreen
import nl.narvekar.abhishek.student649744.ui.theme.Student649744Theme
import nl.narvekar.abhishek.student649744.utils.Session
import nl.narvekar.abhishek.student649744.viewModel.*

class MainActivity : ComponentActivity() {
    // this commit is from MAIN branch
    //private val favoritesViewModel by viewModels<FavoritesViewModel>()
    //private val articleDetailViewModel by viewModels<ArticleDetailViewModel>()
    //private val loginViewModel by viewModels<LoginViewModel>()
    //private val registerViewModel by viewModels<RegisterViewModel>()
    //private val articleViewModel by viewModels<ArticleViewModel>()

    lateinit var connectionLiveData: NetworkConnectionLiveData

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        Session.startSession(this)
        super.onCreate(savedInstanceState)
        connectionLiveData = NetworkConnectionLiveData(this)
        setContent {
            val isNetworkAvailable = connectionLiveData.observeAsState(false).value
            Student649744Theme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    //CheckInternetConnection(isNetworkAvailable)
                    NavigationScreen(isNetworkAvailable)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Student649744Theme {
    }
}
