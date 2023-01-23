package nl.narvekar.abhishek.student649744.viewModel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import nl.narvekar.abhishek.student649744.utils.Session
import nl.narvekar.abhishek.student649744.api.RetrofitInstance
import nl.narvekar.abhishek.student649744.data.ArticleList
import nl.narvekar.abhishek.student649744.data.ArticleMapper

class ArticleDetailViewModel : ViewModel() {

    var errorMessage: String by mutableStateOf("")
    var isLoading = mutableStateOf(false)

    private val retrofit = RetrofitInstance.getInstance()

    private val articleMapper = ArticleMapper()
    private val mutableArticle = MutableStateFlow<ArticleList?>(null)
    var mutableArticleState: StateFlow<ArticleList?> = mutableArticle

    suspend fun getArticleById(Id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                isLoading.value = true
                val authToken = Session.getAuthToken()
                val article = retrofit.getArticleById(authToken, Id)
                articleMapper.mapArticle(article.results)
                mutableArticle.emit(article)
                isLoading.value = false
            }catch (e: Exception) {
                errorMessage = e.message.toString()
                Log.d("", errorMessage)
            }
        }
    }

}