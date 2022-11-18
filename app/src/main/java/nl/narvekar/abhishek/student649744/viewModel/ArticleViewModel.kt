package nl.narvekar.abhishek.student649744.viewModel

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import nl.narvekar.abhishek.student649744.api.NewsApi
import nl.narvekar.abhishek.student649744.data.Article
import nl.narvekar.abhishek.student649744.data.ArticleList

const val PAGE_SIZE_Articles = 20

class ArticleViewModel : ViewModel() {

    var articleListResponse: ArticleList by mutableStateOf(ArticleList())
    var errorMessage: String by mutableStateOf("")
    val progressBar = mutableStateOf(false)
    val page = mutableStateOf(10)
    private var articleListScrollPosition = 0
    val apiService = NewsApi.getInstance()
    init {
        getArticlesByHeader()
    }

    fun getArticlesByHeader() {
        viewModelScope.launch {
            progressBar.value = true
            try {
                val articles = apiService.getAllArticles(page.value)
                articleListResponse = articles
                progressBar.value = false
            } catch (e: Exception) {
                errorMessage = e.message.toString()
                Log.d("Error: ", errorMessage)
            }
        }
    }

    fun nextPage() {
        viewModelScope.launch {
            // prevent duplicate happening too quickly
            //delay(1000)
            val apiService2 = NewsApi.getInstance()
            if ((articleListScrollPosition + 1) >= (page.value + PAGE_SIZE_Articles)) {
                Log.d(TAG, "nextPage: $articleListScrollPosition")
                progressBar.value = true
                incrementPage()
                Log.d(TAG, "nextPage triggered: ${page.value}")
                delay(1000)

                if (page.value > 10) {
                    val result = apiService2.getAllArticles(page.value)
                    Log.d(TAG, "nextPage: ${result}")
                    appendArticles(result.results)
                }
                progressBar.value = false
            }
        }
    }

    // append new recipes to the current list of recipes
    private fun appendArticles(articles: List<Article>) {
        val current = ArrayList(this.articleListResponse.results)
        current.addAll(articles)
        this.articleListResponse.results = current
    }

    private fun incrementPage() {
        page.value = page.value + 1
    }

    fun onChangeArticleScrollPosition(position: Int) {
        articleListScrollPosition = position
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