package com.bangkit.tanikami_xml.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.bangkit.tanikami_xml.data.helper.Response
import com.bangkit.tanikami_xml.data.remote.response.BuyProductResponse
import com.bangkit.tanikami_xml.data.remote.response.GetBuyResponse
import com.bangkit.tanikami_xml.data.remote.retrofit.ApiService
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
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


    fun getBuybyIdTransaksi(id_transaksi: String): LiveData<Response<GetBuyResponse>> = liveData {
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

    suspend fun buyProduct(
        bukti_transfer: File,
        id_ktp: String,
        id_produk: String,
        alamat_penerima: String,
        harga: Int,
        jumlah_beli: Int,
        biaya_pengiriman: Int,
        pajak: Int,
        biaya_admin: Int,
        biaya_total: Int,
        status_pembayaran: Boolean,
        status_pengiriman: Boolean
    ): LiveData<Response<BuyProductResponse>> = liveData {
        emit(Response.Loading)

        val requestedImageBill = bukti_transfer.asRequestBody("image/jpeg".toMediaTypeOrNull())
        val imageMultiPart: MultipartBody.Part = MultipartBody.Part.createFormData(
            "bukti_transfer",
            bukti_transfer.name,
            requestedImageBill
        )

        try {
            apiServ.buyProductNow(
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
        } catch (e: HttpException) {
            emit(Response.Error(e.message.toString()))
            Log.d(TAG, "buyProduct: ${e.message.toString()}")
        }
    }


    companion object {
        private const val TAG = "REPO_PEMBELIAN"
    }

}