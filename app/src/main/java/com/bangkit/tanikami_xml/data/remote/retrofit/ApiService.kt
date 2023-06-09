package com.bangkit.tanikami_xml.data.remote.retrofit

import com.bangkit.tanikami_xml.data.remote.response.ArticleResponse
import com.bangkit.tanikami_xml.data.remote.response.RegisterResponse
import okhttp3.MultipartBody
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface ApiService {

    @Multipart // email belum ada
    @POST("user")
    suspend fun registerUser(
        @Part profil: MultipartBody.Part,
        @Field("id_ktp") id_ktp:String,
        @Field("nama") name: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("telepon") telepon: String,
        @Field("alamat_regist") alamat_regist: String,
        @Field("alamat_penerima") alamat_penerima: String? = null,
        @Field("gender") gender: Boolean,
        @Field("usia") usia: Int,
        @Field("status") status: Boolean,
    ): RegisterResponse

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