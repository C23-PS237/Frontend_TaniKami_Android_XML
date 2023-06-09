package com.bangkit.tanikami_xml.data.remote.retrofit

import com.bangkit.tanikami_xml.data.remote.response.ArticleResponse
import com.bangkit.tanikami_xml.data.remote.response.ProductResponse
import retrofit2.http.Field
import com.bangkit.tanikami_xml.data.remote.response.RegisterResponse
import okhttp3.MultipartBody
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.Part
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {

    @Multipart
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

    @Multipart
    @POST("produk")
    suspend fun sellProduct(
        @Field("id_produk") idProduk:Int,
        @Field("besaran_stok") besaranStok: String,
        @Field("nama_produk") namaProduk:String,
        @Field("harga") harga: Int,
        @Part ("url_gambar") urlGambar: MultipartBody.Part,
        @Field("rek_penjual") rekPenjual: String,
        @Field("id_ktp") idKtp:String,
        @Field("stok") stok: Int,
        @Field("deskripsi_produk") deskripsiProduk: String,
        @Field("nama_bank") namaBank: String,
        @Field("timestamp") timestamp: String
    ): ProductResponse
    @Multipart
    @PUT("produk/{id_produk}")
    fun updateProduct(
        @Field("id_produk") idProduk:Int,
        @Field("besaran_stok") besaranStok: String,
        @Field("nama_produk") namaProduk:String,
        @Field("harga") harga: Int,
        @Part ("url_gambar") urlGambar: MultipartBody.Part,
        @Field("rek_penjual") rekPenjual: String,
        @Field("stok") stok: Int,
        @Field("deskripsi_produk") deskripsiProduk: String,
        @Field("nama_bank") namaBank: String,
        @Field("timestamp") timestamp: String
    ): ProductResponse
    @GET("produk")
    suspend fun getAllProducts():ProductResponse
    @GET("produk/{id_produk}")
    suspend fun getProductbyIdProduct(
        @Path("id_produk") id_produk: Int
    ):ProductResponse

    @GET("produk/{id_ktp}")
    suspend fun getProductbyIdKTP(
        @Path("id_ktp") id: String
    ):ProductResponse
    @DELETE("produk/{id_produk}")
    suspend fun deleteProductbyId(
        @Path("id_produk") id: Int
    ):ProductResponse

    @GET("artikel")
    suspend fun getArticle(): ArticleResponse

    @GET("artikel/{id_artikel}")
    fun getArticleByID(
        @Path("id_artikel") id: Int
    ): ArticleResponse
}