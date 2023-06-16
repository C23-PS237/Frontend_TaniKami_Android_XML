package com.bangkit.tanikami_xml.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.bangkit.tanikami_xml.data.helper.Response
import com.bangkit.tanikami_xml.data.remote.response.BuyProductResponse
import com.bangkit.tanikami_xml.data.remote.response.GetBuyResponse
import com.bangkit.tanikami_xml.data.remote.response.GetByIdTransaksiResponse
import com.bangkit.tanikami_xml.data.remote.response.GetPurchaseBuyerResponse
import com.bangkit.tanikami_xml.data.remote.response.Product
import com.bangkit.tanikami_xml.data.remote.response.UpdatePembelianResponse
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

    fun getListDataBuyByIdProducts(id_products: List<Int>): LiveData<List<Product>> = liveData {
        val listProducts = mutableListOf<Product>()

        try {
            for (i in id_products) {
                val responseProduct = apiServ.getProductbyIdProduct(i)
                listProducts.add(responseProduct.payload)
            }
        } catch (e: HttpException) {
            Log.d("BUYRepository", "getProductByIdProducts: ${e.message}")
        }

        emit(listProducts)
    }

    fun getPurchaseBuyerByIdPenjual(id_ktp: String): LiveData<Response<GetPurchaseBuyerResponse>> = liveData {

        emit(Response.Loading)
        try {
            val response = apiServ.getBuybyIdPenjual(id_ktp)
            emit(Response.Success(response))
        } catch (e: HttpException) {
            Log.d("Repository", "getPurchaseBuyerbyIdPenjual: ${e.message}")
            emit(Response.Error(e.message.toString()))
        }
    }
    fun getBuyByIdTransaksi(id_transaksi: Int): LiveData<Response<GetByIdTransaksiResponse>> = liveData {
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
        status_pengiriman: RequestBody,
        id_penjual: RequestBody
    ): LiveData<Response<BuyProductResponse>> = liveData {
        emit(Response.Loading)

        try {
            val response = apiServ.buyProductNow(
                id_ktp,
                id_produk,
                alamat_penerima,
                harga, jumlah_beli,
                biaya_pengiriman,
                pajak,
                biaya_admin,
                biaya_total,
                status_pembayaran,
                status_pengiriman,
                id_penjual
            )

            emit(Response.Success(response))

        } catch (e: HttpException) {
            emit(Response.Error(e.message.toString()))
            Log.d(TAG, "buyProduct: ${e.message.toString()}")
        }
    }

    fun updatePembelian (
        id_transaksi: Int,
        bukti_tranfer: File,
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
        status_pengiriman: RequestBody,
        id_penjual: RequestBody
    ): LiveData<Response<UpdatePembelianResponse>> = liveData {
        emit(Response.Loading)

        val image_transfer = reduceFileImage(bukti_tranfer)
        val requestedImage = image_transfer.asRequestBody("image/jpeg".toMediaTypeOrNull())
        val imageMultiPart: MultipartBody.Part = MultipartBody.Part.createFormData(
            "bukti_transfer",
            image_transfer.name,
            requestedImage
        )

        try {
            val response = apiServ.updatePembelianByIdTransaksi(
                id_transaksi,
                imageMultiPart,
                id_ktp,
                id_produk,
                alamat_penerima,
                harga,
                jumlah_beli,
                biaya_pengiriman,
                pajak,
                biaya_admin,
                biaya_total,
                status_pembayaran,
                status_pengiriman,
                id_penjual
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