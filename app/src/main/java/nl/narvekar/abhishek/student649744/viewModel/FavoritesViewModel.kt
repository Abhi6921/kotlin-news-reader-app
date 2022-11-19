package nl.narvekar.abhishek.student649744.viewModel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import nl.narvekar.abhishek.student649744.api.NewsApi
import nl.narvekar.abhishek.student649744.data.Article
import nl.narvekar.abhishek.student649744.data.ArticleList

class FavoritesViewModel : ViewModel() {
    var favoritesListResponse: ArticleList by mutableStateOf(ArticleList())
    var errorMessage: String by mutableStateOf("")

    fun getFavoriteArticles(authToken: String) {
        viewModelScope.launch {
            val apiService = NewsApi.getInstance()
            try {
                val articles = apiService.getAllLikedArticles(authToken)
                favoritesListResponse = articles
            } catch (e: Exception) {
                errorMessage = e.message.toString()
                Log.d("Error: ", errorMessage)
            }
        }
    }

    fun likeArticle(authToken: String, id: Int) {
        viewModelScope.launch {
            val apiService = NewsApi.getInstance()
            val response = apiService.addLikedArticle(authToken, id)

            if (response.isSuccessful) {
                Log.d("Added to like article", "article liked")
            }
            else {
                Log.d("Liked Article Error", response.code().toString())
            }
        }
    }

    fun removeArticle(authToken: String, id: Int) {
        viewModelScope.launch {
            val apiService = NewsApi.getInstance()
            val response = apiService.removeLikedArticle(authToken, id)

            if (response.isSuccessful) {
                Log.d("Successful", "article removed")
            }
            else {
                Log.d("Delete Article ERROR", response.code().toString())
            }

        }
    }
}