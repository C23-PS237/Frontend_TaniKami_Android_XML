package com.bangkit.tanikami_xml.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.bangkit.tanikami_xml.data.helper.Response
import com.bangkit.tanikami_xml.data.remote.response.PayloadItem
import com.bangkit.tanikami_xml.data.remote.retrofit.ApiService
import retrofit2.HttpException
import javax.inject.Inject

class ArticleRepository @Inject constructor(
    private val apiServ: ApiService
) {

    fun getAllArticle(): LiveData<Response<List<PayloadItem>>> = liveData {
        emit(Response.Loading)
        try {
            val response = apiServ.getArticle().payload
            emit(Response.Success(response))
        } catch (e: HttpException) {
            Log.d(TAG, "getAllArticle: ${e.message()}")
            emit(Response.Error(e.message.toString()))
        }
    }

    fun getDetailArticle(id_artikel: Int): LiveData<Response<List<PayloadItem>>> = liveData {
        emit(Response.Loading)
        try {
            val response = apiServ.getArticleByID(id_artikel).payload
            emit(Response.Success(response))
        } catch (e: HttpException) {
            Log.d(TAG, "getDetailArticle: ${e.message()}")
            emit(Response.Error(e.message.toString()))
        }
    }


    companion object {
        const val TAG = "ArticleRepository"
    }
}