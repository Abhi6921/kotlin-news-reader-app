package nl.narvekar.abhishek.student649744.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn

class ArticleViewModel : ViewModel() {

    val articles = Pager(PagingConfig(pageSize = 20)) {
        ArticlePager()
    }.flow.cachedIn(viewModelScope)





}