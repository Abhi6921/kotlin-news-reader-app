package nl.narvekar.abhishek.student649744.viewModel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import nl.narvekar.abhishek.student649744.R
import nl.narvekar.abhishek.student649744.Session
import nl.narvekar.abhishek.student649744.api.NewsApi
import nl.narvekar.abhishek.student649744.data.ArticleList

class ArticleDetailViewModel : ViewModel() {
    var articleResponse: ArticleList by mutableStateOf(ArticleList())
    var errorMessage: String by mutableStateOf("")
    fun getArticleById(Id: Int) : ArticleList {
        viewModelScope.launch(Dispatchers.IO) {
            val apiService = NewsApi.getInstance()
            try {
                val authToken = Session.getAuthToken()
                val article = apiService.getArticleById(authToken, Id)
                articleResponse = article
            }catch (e: Exception) {
                errorMessage = e.message.toString()
                Log.d("", errorMessage)
            }
        }
        return articleResponse
    }

}