package nl.narvekar.abhishek.student649744.viewModel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import nl.narvekar.abhishek.student649744.api.NewsApi
import nl.narvekar.abhishek.student649744.data.ArticleList

class ArticleViewModel : ViewModel() {

    var articleListResponse: ArticleList by mutableStateOf(ArticleList())
    var errorMessage: String by mutableStateOf("")
    val progressBar = mutableStateOf(false)

    fun getArticlesByHeader() {
        viewModelScope.launch {
            progressBar.value = true
            val apiService = NewsApi.getInstance()
            try {
                //delay(7000)
                val articles = apiService.getAllArticlesByHeader()
                articleListResponse = articles
                progressBar.value = false
            } catch (e: Exception) {
                errorMessage = e.message.toString()
                //Log.d("Error:  $errorMessage")
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