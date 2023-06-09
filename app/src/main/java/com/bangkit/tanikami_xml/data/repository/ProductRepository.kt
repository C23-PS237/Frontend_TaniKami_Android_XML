package com.bangkit.tanikami_xml.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.bangkit.tanikami_xml.data.helper.Response
import com.bangkit.tanikami_xml.data.remote.response.ProductResponse
import com.bangkit.tanikami_xml.data.remote.retrofit.ApiService
import okhttp3.MultipartBody
import retrofit2.HttpException
import javax.inject.Inject

class ProductRepository @Inject constructor(
    private val apiServ: ApiService
){

    fun sellProduct(
        idProduk:Int,
        besaranStok: String,
        namaProduk:String,
        harga: Int,
        urlGambar: MultipartBody.Part,
        rekPenjual: String,
        idKtp:String,
        stok: Int,
        deskripsiProduk: String,
        namaBank: String,
        timestamp: String
    ): LiveData<Response<ProductResponse>> = liveData{
        emit(Response.Loading)
        try {
            val response = apiServ.sellProduct( idProduk,
                                                besaranStok,
                                                namaProduk,
                                                harga,
                                                urlGambar,
                                                rekPenjual,
                                                idKtp,
                                                stok,
                                                deskripsiProduk,
                                                namaBank,
                                                timestamp )
            emit(Response.Success(response))
        } catch (e: HttpException) {
            Log.d("Repository", "sellProduct: ${e.message}")
            emit(Response.Error(e.message.toString()))
        }
    }
    fun getAllProducts(): LiveData<Response<ProductResponse>> = liveData {
        emit(Response.Loading)
        try {
            val response = apiServ.getAllProducts()
            emit(Response.Success(response))
        } catch (e: HttpException) {
            Log.d("Repository", "getAllProducts: ${e.message}")
            emit(Response.Error(e.message.toString()))
        }
    }

    fun getProductbyIdProduct(id_produk: Int): LiveData<Response<ProductResponse>> = liveData {
        emit(Response.Loading)
        try {
            val response = apiServ.getProductbyIdProduct(id_produk)
            emit(Response.Success(response))
        } catch (e: HttpException) {
            Log.d("Repository", "getProductbyIdProduct: ${e.message}")
            emit(Response.Error(e.message.toString()))
        }
    }

    fun getProductbyIdKTP(id_ktp: String): LiveData<Response<ProductResponse>> = liveData {
        emit(Response.Loading)
        try {
            val response = apiServ.getProductbyIdKTP(id_ktp)
            emit(Response.Success(response))
        } catch (e: HttpException) {
            Log.d("Repository", "getProductbyIdKTP: ${e.message}")
            emit(Response.Error(e.message.toString()))
        }
    }
//    fun getArticle(): LiveData<Response<List<Article>>> = liveData {
//        emit(Response.Loading)
//        try {
//            emit(Response.Success(FakeDataSource.articleBasis))
//        } catch (e: HttpException) {
//            Log.d("Repository", "getArticle: ${e.message}")
//            emit(Response.Error(e.message.toString()))
//        }
//    }
//
//    fun getDetailArticle(id_artikel: Int): LiveData<Response<Article>> = liveData {
//        emit(Response.Loading)
//        try {
//            FakeDataSource.articleBasis.forEach {
//                if (it.id_artikel == id_artikel) {
//                    emit(Response.Success(it))
//                }
//            }
//        } catch (e: Exception) {
//            Log.d("RepoDetail", "getDetailArticle: ${e.message}")
//            emit(Response.Error(e.message.toString()))
//        }
//    }
}