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
import nl.narvekar.abhishek.student649744.api.RetrofitInstance
import nl.narvekar.abhishek.student649744.data.ArticleList

class ArticleDetailViewModel : ViewModel() {
    var articleResponse: ArticleList by mutableStateOf(ArticleList())
    var errorMessage: String by mutableStateOf("")
    var isLoading = mutableStateOf(false)

    private val retrofit = RetrofitInstance.getInstance()
    //private val apiInterface = retrofit.create(NewsApi::class.java)

    fun getArticleById(Id: Int) : ArticleList {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                isLoading.value = true
                val authToken = Session.getAuthToken()
                val article = retrofit.getArticleById(authToken, Id)
                articleResponse = article
                isLoading.value = false
            }catch (e: Exception) {
                errorMessage = e.message.toString()
                Log.d("", errorMessage)
            }
        }
        return articleResponse
    }

}