package nl.narvekar.abhishek.student649744.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn

class ArticleViewModel(authToken: String) : ViewModel() {

    //var authToken: String by  mutableStateOf("")

    val articles = Pager(PagingConfig(pageSize = 20)) {
        ArticlePager(authToken)
    }.flow.cachedIn(viewModelScope)

}