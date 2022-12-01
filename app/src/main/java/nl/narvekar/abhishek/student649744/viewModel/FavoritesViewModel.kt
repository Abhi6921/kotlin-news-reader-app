package nl.narvekar.abhishek.student649744.viewModel

import android.provider.Settings.Secure.getString
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import nl.narvekar.abhishek.student649744.R
import nl.narvekar.abhishek.student649744.Session
import nl.narvekar.abhishek.student649744.api.NewsApi
import nl.narvekar.abhishek.student649744.api.RetrofitInstance
import nl.narvekar.abhishek.student649744.data.Article
import nl.narvekar.abhishek.student649744.data.ArticleList

class FavoritesViewModel : ViewModel() {
    var favoritesListResponse: ArticleList by mutableStateOf(ArticleList())
    var errorMessage: String by mutableStateOf("")
    var isLoading = mutableStateOf(false)

    private val retrofit = RetrofitInstance.getInstance()
    //private val apiInterface = retrofit.create(NewsApi::class.java)

    fun getFavoriteArticles() {
        viewModelScope.launch {
            try {
                isLoading.value = true
                val authToken = Session.getAuthToken()
                val articles = retrofit.getAllLikedArticles(authToken)
                if (articles.results.isEmpty()) {
                    isLoading.value = true
                    errorMessage = "You have no favorite articles"
                    isLoading.value = false
                }
                favoritesListResponse = articles
                isLoading.value = false
            } catch (e: Exception) {
                errorMessage = e.message.toString()
                Log.d("Error: ", errorMessage)
            }
        }
    }

    fun likeArticle(id: Int) {
        viewModelScope.launch {

            // val authToken = Session.getAuthToken()
            val response = retrofit.addLikedArticle(Session.getAuthToken(), id)

            if (response.isSuccessful) {
                Log.d("Added to like article", "article liked")
            }
            else {
                Log.d("Liked Article Error", response.code().toString())
            }
        }
    }

    fun removeArticle(id: Int) {
        viewModelScope.launch {
            val authToken = Session.getAuthToken()
            val response = retrofit.removeLikedArticle(authToken, id)

            if (response.isSuccessful) {
                Log.d("Successful", "article removed")
            }
            else {
                Log.d("Delete Article ERROR", response.code().toString())
            }

        }
    }
}