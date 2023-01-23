package nl.narvekar.abhishek.student649744.viewModel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import nl.narvekar.abhishek.student649744.utils.Session
import nl.narvekar.abhishek.student649744.api.RetrofitInstance
import nl.narvekar.abhishek.student649744.data.ArticleList

class FavoritesViewModel : ViewModel() {


    var errorMessage: String by mutableStateOf("")
    var isLoading = mutableStateOf(false)

    private val retrofit = RetrofitInstance.getInstance()

    private val mutableFavoriteArticle = MutableStateFlow<ArticleList?>(null)
    var mutableFavoriteArticleState: StateFlow<ArticleList?> = mutableFavoriteArticle

    fun getFavoriteArticles() {
        viewModelScope.launch {
            try {
                isLoading.value = true
                val authToken = Session.getAuthToken()
                val articles = retrofit.getAllLikedArticles(authToken)
                //favoritesListResponse = articles
                mutableFavoriteArticle.tryEmit(articles)
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