package nl.narvekar.abhishek.student649744.viewModel

import android.content.SharedPreferences
import androidx.compose.ui.platform.LocalContext
import androidx.paging.PagingSource
import androidx.paging.PagingState
import nl.narvekar.abhishek.student649744.Constants
import nl.narvekar.abhishek.student649744.Constants.AUTH_TOKEN_KEY
import nl.narvekar.abhishek.student649744.api.NewsApi
import nl.narvekar.abhishek.student649744.api.RetrofitInstance
import nl.narvekar.abhishek.student649744.data.Article
import nl.narvekar.abhishek.student649744.data.ArticleList
import nl.narvekar.abhishek.student649744.data.ArticleMapper

class ArticlePager(var authToken: String): PagingSource<Int, Article>() {

    private val articleMapper = ArticleMapper()

    private val api = NewsApi.getInstance()
    //private val retorfit = RetrofitInstance.buildService(NewsApi::class.java)

    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return null
    }
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        val nextPage = params.key ?: 1

        val result = fetch(nextPage, params.loadSize)
            .getOrElse {
                return LoadResult.Error(it)
            }
        return LoadResult.Page(result.results, if (nextPage == 1) null else nextPage - 1, (params.key ?: 0) + 1)
    }

    private suspend fun fetch(startkey: Int, loadSize: Int) : Result<ArticleList> {

        val response = api.getAllArticlesPaging(authToken, startkey * loadSize)

        return when {
            response.isSuccessful -> {
                val body = response.body()
                if (body !==  null) {
                    articleMapper.map(body)
                } else {
                    Result.failure(IllegalStateException("body was empty"))
                }
            }
            else -> Result.failure(IllegalStateException("something went wrong"))
        }
    }



}