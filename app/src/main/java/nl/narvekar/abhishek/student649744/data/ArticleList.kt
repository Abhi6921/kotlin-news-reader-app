package nl.narvekar.abhishek.student649744.data

import com.google.gson.annotations.SerializedName

data class ArticleList(
    val NextId: Int? = null,
    @SerializedName("Results")
    var results: List<Article> = arrayListOf()
)