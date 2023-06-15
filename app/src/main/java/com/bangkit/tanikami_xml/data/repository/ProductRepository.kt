package com.bangkit.tanikami_xml.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.bangkit.tanikami_xml.data.helper.Response
import com.bangkit.tanikami_xml.data.remote.response.DetailProductResponse
import com.bangkit.tanikami_xml.data.remote.response.ProductResponse
import com.bangkit.tanikami_xml.data.remote.response.ProductUpdateStockResponse
import com.bangkit.tanikami_xml.data.remote.response.SellProductResponse
import com.bangkit.tanikami_xml.data.remote.retrofit.ApiService
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.HttpException
import javax.inject.Inject

class ProductRepository @Inject constructor(
    //private val userPref: UserPreference,
    private val apiServ: ApiService
){

    fun sellProduct(
        //idProduk:Int,
        besaran_stok: RequestBody,
        nama_produk:RequestBody,
        harga: RequestBody,
        gambar_produk: MultipartBody.Part,
        rek_penjual: RequestBody,
        id_ktp:RequestBody,
        stok: RequestBody,
        deskripsi_produk: RequestBody,
        nama_bank: RequestBody,
        //timestamp: String
    ): LiveData<Response<SellProductResponse>> = liveData{
        emit(Response.Loading)
        try {
            val response = apiServ.sellProduct(
                //idProduk,
                                                besaran_stok,
                                                nama_produk,
                                                harga,
                                                gambar_produk,
                                                rek_penjual,
                                                id_ktp,
                                                stok,
                                                deskripsi_produk,
                                                nama_bank
                                                //timestamp
                                                )
            emit(Response.Success(response))
        } catch (e: HttpException) {
            Log.d("Repository", "sellProduct: ${e.message}")
            emit(Response.Error(e.message.toString()))
        }
    }

    fun setStock(id_produk: Int): LiveData<Int> = liveData {
        try {
            var sumStock = 0
            val response1 = apiServ.getPenjualanByIdProduk(id_produk).payload
            for (item in response1) {
                sumStock += item.jumlahDibeli
            }

            emit(sumStock)
        } catch (e: HttpException) {
            Log.d("Repository", "sellProduct: ${e.message}")
        }
    }

    fun updateProductStock(id_produk: Int, stok: Int): LiveData<Response<ProductUpdateStockResponse>> = liveData {
        emit(Response.Loading)
        try {
            val response = apiServ.updateStockInProduct(id_produk, stok)
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

    fun getProductbyIdProduct(id_produk: Int): LiveData<Response<DetailProductResponse>> = liveData {
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