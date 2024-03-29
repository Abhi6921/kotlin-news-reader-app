package nl.narvekar.abhishek.student649744.api

import nl.narvekar.abhishek.student649744.data.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*


interface NewsApi {
    
    @Headers("Content-Type:application/json")
    @POST("api/Users/login")
    fun loginUser(@Body user: User) : Call<LoginResponse>

    @Headers("Content-Type:application/json")
    @POST("api/Users/register")
    fun postANewUser(@Body user: User?) : Call<RegisterUserResponse>

    @GET("api/Articles/{id}")
    suspend fun getArticleById(@Header("x-authtoken") authToken: String, @Path("id") Id: Int) : ArticleList


    @Headers("Content-Type:application/json")
    @GET("api/Articles")
    suspend fun getAllArticles(@Header("x-authtoken") authToken: String, @Query("count") count: Int) : Response<ArticleList>


    @GET("api/Articles/liked")
    suspend fun getAllLikedArticles(@Header("x-authtoken") authToken: String) : ArticleList

    @PUT("api/Articles/{id}/like")
    suspend fun addLikedArticle(
        @Header("x-authtoken") authToken: String,
        @Path("id") articleId: Int
    ): Response<Unit>

    @DELETE("api/Articles/{id}/like")
    suspend fun removeLikedArticle(
        @Header("x-authtoken") authToken: String,
        @Path("id") articleId: Int
    ) : Response<Unit>

}