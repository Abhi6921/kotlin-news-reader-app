package nl.narvekar.abhishek.student649744.data

data class Article(
    val Id: Int,
    val Feed: Int,
    val Title: String,
    val Summary: String,
    val PublishDate: String,
    val Image: String,
    val Url: String,
    var IsLiked: Boolean
)