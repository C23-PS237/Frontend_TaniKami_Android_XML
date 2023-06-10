package com.bangkit.tanikami_xml.data.remote.retrofit

import com.bangkit.tanikami_xml.data.remote.response.ArticleResponse
import com.bangkit.tanikami_xml.data.remote.response.ArtikelDetaiilResponse
import com.bangkit.tanikami_xml.data.remote.response.DetailProductResponse
import com.bangkit.tanikami_xml.data.remote.response.LoginResponse
import com.bangkit.tanikami_xml.data.remote.response.ProductResponse
import retrofit2.http.Field
import com.bangkit.tanikami_xml.data.remote.response.RegisterResponse
import com.bangkit.tanikami_xml.data.remote.response.SellProductResponse
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
        @Part("id_ktp") id_ktp:String,
        @Part("nama") name: String,
        @Part("email") email: String,
        @Part("password") password: String,
        @Part("telepon") telepon: String,
        @Part("alamat_regist") alamat_regist: String,
        @Part("alamat_penerima") alamat_penerima: String? = null,
        @Part("gender") gender: Boolean,
        @Part("usia") usia: Int,
        @Part("status") status: Boolean,
    ): RegisterResponse

    @GET("user/{id_ktp}")
    suspend fun loginUser(
        @Path("id_ktp") id_ktp: String
    ): LoginResponse

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
    ): SellProductResponse
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
    ): DetailProductResponse

    @GET("produk/{id_ktp}")
    suspend fun getProductbyIdKTP(
        @Path("id_ktp") id: String
    ):DetailProductResponse

    @DELETE("produk/{id_produk}")
    suspend fun deleteProductbyId(
        @Path("id_produk") id: Int
    ):ProductResponse

    @GET("artikel")
    suspend fun getArticle(): ArticleResponse

    @GET("artikel/{id_artikel}")
    suspend fun getArticleByID(
        @Path("id_artikel") id: String
    ): ArtikelDetaiilResponse
}