package nl.narvekar.abhishek.student649744.viewModel

import androidx.paging.PagingSource
import androidx.paging.PagingState
import nl.narvekar.abhishek.student649744.utils.Session
import nl.narvekar.abhishek.student649744.api.RetrofitInstance
import nl.narvekar.abhishek.student649744.data.Article
import nl.narvekar.abhishek.student649744.data.ArticleList
import nl.narvekar.abhishek.student649744.data.ArticleMapper

class ArticlePager: PagingSource<Int, Article>() {

    private val articleMapper = ArticleMapper()

    private val retrofit = RetrofitInstance.getInstance()

    override val keyReuseSupported: Boolean = true
    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return null
    }
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        val nextPage = params.key ?: 1

        val result = fetch(nextPage, params.loadSize)
            .getOrElse {
                return LoadResult.Error(it)
            }
        return LoadResult.Page(result.results, null, (params.key ?: 0) + 1)
    }

    private suspend fun fetch(startkey: Int, loadSize: Int) : Result<ArticleList> {

        val authToken = Session.getAuthToken()
        val response = retrofit.getAllArticles(authToken, loadSize.coerceIn(1, 30))

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