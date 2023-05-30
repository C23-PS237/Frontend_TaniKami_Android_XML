package com.bangkit.tanikami_xml.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.bangkit.tanikami_xml.data.helper.Response
import com.bangkit.tanikami_xml.data.model.FakeDataSource
import com.bangkit.tanikami_xml.data.model.Product
import com.bangkit.tanikami_xml.data.model.Article
import retrofit2.HttpException

class ProductRepository {

    fun getAllProduct(): LiveData<Response<List<Product>>> = liveData {
        emit(Response.Loading)
        try {
            emit(Response.Success(FakeDataSource.fakeDataProduct))
        } catch (e: HttpException) {
            Log.d("Repository", "getAllProducts: ${e.message}")
            emit(Response.Error(e.message.toString()))
        }
    }

    fun getArticle(): LiveData<Response<List<Article>>> = liveData {
        emit(Response.Loading)
        try {
            emit(Response.Success(FakeDataSource.articleBasis))
        } catch (e: HttpException) {
            Log.d("Repository", "getArticle: ${e.message}")
            emit(Response.Error(e.message.toString()))
        }
    }
}