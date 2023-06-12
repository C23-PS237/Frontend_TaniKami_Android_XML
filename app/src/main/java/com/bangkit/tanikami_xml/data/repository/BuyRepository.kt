package com.bangkit.tanikami_xml.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.bangkit.tanikami_xml.data.helper.Response
import com.bangkit.tanikami_xml.data.remote.response.GetBuyResponse
import com.bangkit.tanikami_xml.data.remote.retrofit.ApiService
import retrofit2.HttpException
import javax.inject.Inject

class BuyRepository @Inject constructor(
    //private val userPref: UserPreference,
    private val apiServ: ApiService
){
    fun getBuybyId(id_transaksi: String): LiveData<Response<GetBuyResponse>> = liveData {
        emit(Response.Loading)
        try {
            val response = apiServ.getBuybyId(id_transaksi)
            emit(Response.Success(response))
        } catch (e: HttpException) {
            Log.d("Repository", "getProductbyIdTransaksi: ${e.message}")
            emit(Response.Error(e.message.toString()))
        }
    }
}