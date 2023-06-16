package com.bangkit.tanikami_xml.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.bangkit.tanikami_xml.data.helper.Response
import com.bangkit.tanikami_xml.data.remote.response.DetailProductResponse
import com.bangkit.tanikami_xml.data.remote.response.ProductResponse
import com.bangkit.tanikami_xml.data.remote.response.ProductUpdateResponse
import com.bangkit.tanikami_xml.data.remote.response.ProductUpdateStockResponse
import com.bangkit.tanikami_xml.data.remote.response.SellProductResponse
import com.bangkit.tanikami_xml.data.remote.retrofit.ApiService
import com.bangkit.tanikami_xml.reduceFileImage
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.HttpException
import java.io.File
import javax.inject.Inject

class ProductRepository @Inject constructor(
    private val apiServ: ApiService
){

    fun sellProduct(
        besaran_stok: RequestBody,
        nama_produk:RequestBody,
        harga: RequestBody,
        gambar_produk: MultipartBody.Part,
        rek_penjual: RequestBody,
        id_ktp:RequestBody,
        stok: RequestBody,
        deskripsi_produk: RequestBody,
        nama_bank: RequestBody,
    ): LiveData<Response<SellProductResponse>> = liveData{
        emit(Response.Loading)
        try {
            val response = apiServ.sellProduct(
                                                besaran_stok,
                                                nama_produk,
                                                harga,
                                                gambar_produk,
                                                rek_penjual,
                                                id_ktp,
                                                stok,
                                                deskripsi_produk,
                                                nama_bank
                                                )
            emit(Response.Success(response))
        } catch (e: HttpException) {
            Log.d("Repository", "sellProduct: ${e.message}")
            emit(Response.Error(e.message.toString()))
        }
    }

    fun editUpdateProduct(
        id_produk: Int,
        gambar_produk: File,
        nama_produk: RequestBody,
        besaran_stok: RequestBody,
        stok: RequestBody,
        harga: RequestBody,
        deskripsi_produk: RequestBody,
        nama_bank: RequestBody,
        rek_penjual: RequestBody
    ): LiveData<Response<ProductUpdateResponse>> = liveData {

        val imageFile = reduceFileImage(gambar_produk)
        val requestedImage = imageFile.asRequestBody("image/jpeg".toMediaTypeOrNull())
        val imageMultiPart: MultipartBody.Part = MultipartBody.Part.createFormData(
            "gambar_produk",
            imageFile.name,
            requestedImage
        )

        try {
            val response = apiServ.updateProduct(
                id_produk,
                imageMultiPart,
                nama_produk,
                besaran_stok,
                stok,
                harga,
                deskripsi_produk,
                nama_bank,
                rek_penjual
            )
            emit(Response.Success(response))
        } catch (e: HttpException) {
            Log.d("Repository", "updateProduct: ${e.message}")
            emit(Response.Error(e.message.toString()))
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
}