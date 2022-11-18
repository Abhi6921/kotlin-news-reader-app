package nl.narvekar.abhishek.student649744.data

class ArticleMapper {

    fun mapArticle(entity: List<Article>) : Result<List<Article>> = runCatching {
        entity.map {
            with(it) {
                Article(
                    Id,
                    Feed,
                    Title,
                    Summary,
                    PublishDate,
                    Image,
                    Url,
                    IsLiked
                )
            }
        }
    }

    fun map(entity: ArticleList) : Result<ArticleList> = runCatching {
        with(entity) {
           ArticleList(
               NextId = NextId!!,
               results = mapArticle(results).getOrThrow()
           )
        }
    }
}