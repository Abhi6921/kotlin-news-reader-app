package nl.narvekar.abhishek.student649744.viewModel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import nl.narvekar.abhishek.student649744.api.NewsApi
import nl.narvekar.abhishek.student649744.data.ArticleList

class ArticleDetailViewModel : ViewModel() {
    var articleResponse: ArticleList by mutableStateOf(ArticleList())
    var errorMessage: String by mutableStateOf("")

    fun getArticleById(authToken: String, Id: Int) : ArticleList {
        viewModelScope.launch(Dispatchers.IO) {
            val apiService = NewsApi.getInstance()
            try {
                val article = apiService.getArticleById(authToken, Id)
                articleResponse = article
            }catch (e: Exception) {
                errorMessage = e.message.toString()
                //Log.d("Error:  $errorMessage")
                Log.d("Error: ", errorMessage)
            }
        }
        return articleResponse
    }
}