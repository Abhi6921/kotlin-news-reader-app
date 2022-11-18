package nl.narvekar.abhishek.student649744.viewModel

import androidx.paging.PagingSource
import androidx.paging.PagingState
import nl.narvekar.abhishek.student649744.api.NewsApi
import nl.narvekar.abhishek.student649744.data.Article

class ArticlePaging: PagingSource<Int, Article>() {

    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition?.let { position ->
            val page = state.closestPageToPosition(position)
            page?.prevKey?.minus(1) ?: page?.nextKey?.plus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        val apiService = NewsApi.getInstance()

        return try {
            val page = params.key ?: 10
            val response = apiService.getAllArticlesPaging(page)
            LoadResult.Page(
                data = response.results,
                prevKey = null,
                nextKey = if (response.results.isNotEmpty()) { response.NextId?.plus(1) } else { null }
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}