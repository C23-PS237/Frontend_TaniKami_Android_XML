package com.bangkit.tanikami_xml.data.remote.retrofit

import com.bangkit.tanikami_xml.data.remote.response.ArticleResponse
import com.bangkit.tanikami_xml.data.remote.response.ArtikelDetaiilResponse
import com.bangkit.tanikami_xml.data.remote.response.BuyProductResponse
import com.bangkit.tanikami_xml.data.remote.response.DetailProductResponse
import com.bangkit.tanikami_xml.data.remote.response.GetBuyResponse
import com.bangkit.tanikami_xml.data.remote.response.GetByIdTransaksiResponse
import com.bangkit.tanikami_xml.data.remote.response.GetPurchaseBuyerResponse
import com.bangkit.tanikami_xml.data.remote.response.LoginResponse
import com.bangkit.tanikami_xml.data.remote.response.ProductResponse
import com.bangkit.tanikami_xml.data.remote.response.RegisterResponse
import com.bangkit.tanikami_xml.data.remote.response.SellProductResponse
import com.bangkit.tanikami_xml.data.remote.response.UpdatePembelianResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.Part
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {

    //User
    @Multipart
    @POST("user")
    suspend fun registerUser(
        @Part profil: MultipartBody.Part,
        @Part("id_ktp") id_ktp:RequestBody,
        @Part("nama") name: RequestBody,
        @Part("email") email: RequestBody,
        @Part("password") password: RequestBody,
        @Part("telepon") telepon: RequestBody,
        @Part("alamat_regist") alamat_regist: RequestBody,
        @Part("alamat_penerima") alamat_penerima: RequestBody? = null,
        @Part("gender") gender: RequestBody,
        @Part("usia") usia: RequestBody,
        @Part("status") status: RequestBody,
    ): RegisterResponse

    @GET("user/{id_ktp}")
    suspend fun loginUser(
        @Path("id_ktp") id_ktp: String
    ): LoginResponse

    //Produk
    @Multipart
    @POST("produk")
    suspend fun sellProduct(
        //@Part("id_produk") idProduk:Int,
        @Part("besaran_stok") besaran_stok: RequestBody,
        @Part("nama_produk") nama_produk:RequestBody,
        @Part("harga") harga: RequestBody,
        @Part gambar_produk: MultipartBody.Part,
        @Part("rek_penjual") rek_penjual: RequestBody,
        @Part("id_ktp") id_ktp:RequestBody,
        @Part("stok") stok: RequestBody,
        @Part("deskripsi_produk") deskripsi_produk: RequestBody,
        @Part("nama_bank") nama_bank: RequestBody,
        //@Part("timestamp") timestamp: String
    ): SellProductResponse

    @Multipart
    @PUT("produk/{id_produk}")
    fun updateProduct(
        //@Part("id_produk") idProduk:Int,
        @Part("besaran_stok") besaranStok: String,
        @Part("nama_produk") namaProduk:String,
        @Part("harga") harga: Int,
        @Part gambar_produk: MultipartBody.Part,
        @Part("rek_penjual") rekPenjual: String,
        @Part("stok") stok: Int,
        @Part("deskripsi_produk") deskripsiProduk: String,
        @Part("nama_bank") namaBank: String,
        @Part("timestamp") timestamp: String
    ): ProductResponse

    @GET("produk")
    suspend fun getAllProducts():ProductResponse
    @GET("produk/{id_produk}")
    suspend fun getProductbyIdProduct(
        @Path("id_produk") id_produk: Int
    ): DetailProductResponse

    @GET("produk/ktp/{id_ktp}")
    suspend fun getProductbyIdKTP(
        @Path("id_ktp") id: String
    ):ProductResponse

    @DELETE("produk/{id_produk}")
    suspend fun deleteProductbyId(
        @Path("id_produk") id: Int
    ):ProductResponse

    //Pembelian
    @GET("pembelian/ktp/{id_ktp}")
    suspend fun getBuybyIdKtp(
        @Path("id_ktp") id: String
    ): GetBuyResponse

    @Multipart
    @POST("pembelian")
    suspend fun buyProductNow(
        @Part("id_ktp") id_ktp: RequestBody,
        @Part("id_produk") id_produk: RequestBody,
        @Part("alamat_penerima") alamat_penerima: RequestBody,
        @Part("harga") harga: RequestBody,
        @Part("jumlah_dibeli") jumlah_beli: RequestBody,
        @Part("biaya_pengiriman") biaya_pengiriman: RequestBody,
        @Part("pajak") pajak: RequestBody,
        @Part("biaya_admin") biaya_admin: RequestBody,
        @Part("biaya_total") biaya_total: RequestBody,
        @Part("status_pembayaran") statusPembayaran: RequestBody,
        @Part("status_pengiriman") statusPengiriman: RequestBody,
        @Part("id_penjual") id_penjual: RequestBody
    ): BuyProductResponse

    @Multipart
    @PUT("pembelian/{id_transaksi}")
    suspend fun updatePembelianByIdTransaksi(
        @Path("id_transaksi") id_transaksi: Int,
        @Part bukti_transfer: MultipartBody.Part,
        @Part("id_ktp") id_ktp: RequestBody,
        @Part("id_produk") id_produk: RequestBody,
        @Part("alamat_penerima") alamat_penerima: RequestBody,
        @Part("harga") harga: RequestBody,
        @Part("jumlah_dibeli") jumlah_beli: RequestBody,
        @Part("biaya_pengiriman") biaya_pengiriman: RequestBody,
        @Part("pajak") pajak: RequestBody,
        @Part("biaya_admin") biaya_admin: RequestBody,
        @Part("biaya_total") biaya_total: RequestBody,
        @Part("status_pembayaran") statusPembayaran: RequestBody,
        @Part("status_pengiriman") statusPengiriman: RequestBody,
        @Part("id_penjual") id_penjual: RequestBody
    ): UpdatePembelianResponse

    @GET("pembelian/{id_transaksi}")
    suspend fun getBuybyIdTransaksi(
        @Path("id_transaksi") id: Int
    ): GetByIdTransaksiResponse

    @GET("pembelian/penjual/{id_penjual}")
    suspend fun getBuybyIdPenjual(
        @Path("id_penjual") id_penjual:String
    ): GetPurchaseBuyerResponse

    //artikel
    @GET("artikel")
    suspend fun getArticle(): ArticleResponse

    @GET("artikel/{id_artikel}")
    suspend fun getArticleByID(
        @Path("id_artikel") id: String
    ): ArtikelDetaiilResponse
}