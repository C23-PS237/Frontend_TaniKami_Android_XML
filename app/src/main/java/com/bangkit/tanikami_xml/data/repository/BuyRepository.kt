package com.bangkit.tanikami_xml.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.bangkit.tanikami_xml.data.helper.Response
import com.bangkit.tanikami_xml.data.remote.response.BuyProductResponse
import com.bangkit.tanikami_xml.data.remote.response.GetBuyResponse
import com.bangkit.tanikami_xml.data.remote.response.Product
import com.bangkit.tanikami_xml.data.remote.response.ProductResponse
import com.bangkit.tanikami_xml.data.remote.retrofit.ApiService
import com.bangkit.tanikami_xml.reduceFileImage
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.HttpException
import java.io.File
import javax.inject.Inject

class BuyRepository @Inject constructor(
    //private val userPref: UserPreference,
    private val apiServ: ApiService
){
    fun getBuybyIdKtp(id_ktp: String): LiveData<Response<GetBuyResponse>> = liveData {
        emit(Response.Loading)
        try {
            val response = apiServ.getBuybyIdKtp(id_ktp)
            emit(Response.Success(response))
        } catch (e: HttpException) {
            Log.d("Repository", "getBuybyIdKtp: ${e.message}")
            emit(Response.Error(e.message.toString()))
        }
    }

    fun getListDataBuyByIdProducts(id_products: List<Int>): LiveData<Response<List<Product>>> = liveData {
        emit(Response.Loading)
        try {
            val listProducts = mutableListOf<Product>()
            for (i in id_products) {
                val responseProduct = apiServ.getProductbyIdProduct(i)
                listProducts.add(responseProduct.payload)
            }

            emit(Response.Success(listProducts))
        } catch (e: HttpException) {
            Log.d("BUYRepository", "getProductByIdProducts: ${e.message}")
            emit(Response.Error(e.message.toString()))
        }
    }


    fun getBuyByIdTransaksi(id_transaksi: String): LiveData<Response<GetBuyResponse>> = liveData {
        //suspend fun getProductByIdTransaksi(id_transaksi: String):
        emit(Response.Loading)
        try {
            val response = apiServ.getBuybyIdTransaksi(id_transaksi)
            emit(Response.Success(response))
        } catch (e: HttpException) {
            Log.d("Repository", "getProductbyIdTransaksi: ${e.message}")
            emit(Response.Error(e.message.toString()))
        }
    }

    fun buyProduct(
        bukti_transfer: File,
        id_ktp: RequestBody,
        id_produk: RequestBody,
        alamat_penerima: RequestBody,
        harga: RequestBody,
        jumlah_beli: RequestBody,
        biaya_pengiriman: RequestBody,
        pajak: RequestBody,
        biaya_admin: RequestBody,
        biaya_total: RequestBody,
        status_pembayaran: RequestBody,
        status_pengiriman: RequestBody
    ): LiveData<Response<BuyProductResponse>> = liveData {
        emit(Response.Loading)

        val imageFile = reduceFileImage(bukti_transfer)
        val requestedImageBill = imageFile.asRequestBody("image/jpeg".toMediaTypeOrNull())
        val imageMultiPart: MultipartBody.Part = MultipartBody.Part.createFormData(
            "bukti_transfer",
            imageFile.name,
            requestedImageBill
        )

        try {
            val response = apiServ.buyProductNow(
                imageMultiPart,
                id_ktp,
                id_produk,
                alamat_penerima,
                harga, jumlah_beli,
                biaya_pengiriman,
                pajak,
                biaya_admin,
                biaya_total,
                status_pembayaran,
                status_pengiriman
            )

            emit(Response.Success(response))

        } catch (e: HttpException) {
            emit(Response.Error(e.message.toString()))
            Log.d(TAG, "buyProduct: ${e.message.toString()}")
        }
    }


    companion object {
        private const val TAG = "REPO_PEMBELIAN"
    }

}