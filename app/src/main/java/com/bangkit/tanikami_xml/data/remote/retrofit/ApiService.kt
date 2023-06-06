package com.bangkit.tanikami_xml.data.remote.retrofit

import com.bangkit.tanikami_xml.data.remote.response.ArticleResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

//    @FormUrlEncoded // email belum ada
//    @POST
//    fun registerUser(
//        @Field("id_ktp") id_ktp:String,
//        @Field("nama") name: String,
//        @Field("")
//    )

//    @FormUrlEncoded // error
//    @POST("produk/{id_ktp}")
//    fun sellProduct(

//    ):

    @GET("artikel")
    suspend fun getArticle(): ArticleResponse

    @GET("artikel/{id_artikel}")
    fun getArticleByID(
        @Path("id_artikel") id: Int
    ): ArticleResponse
}