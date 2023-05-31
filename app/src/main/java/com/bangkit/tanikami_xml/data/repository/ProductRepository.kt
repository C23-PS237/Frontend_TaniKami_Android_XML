package com.bangkit.tanikami_xml.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.bangkit.tanikami_xml.data.helper.Response
import com.bangkit.tanikami_xml.data.model.FakeDataSource
import com.bangkit.tanikami_xml.data.model.Product
import com.bangkit.tanikami_xml.data.model.Article
import retrofit2.HttpException
import java.lang.Exception

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

    fun getDetailArticle(id_artikel: Int): LiveData<Response<Article>> = liveData {
        emit(Response.Loading)
        var data: Article? = null
        try {
            FakeDataSource.articleBasis.forEach {
                if (it.id_artikel == id_artikel) {
                    emit(Response.Success(it))
                }
            }
        } catch (e: Exception) {
            Log.d("RepoDetail", "getDetailArticle: ${e.message}")
            emit(Response.Error(e.message.toString()))
        }
    }
}