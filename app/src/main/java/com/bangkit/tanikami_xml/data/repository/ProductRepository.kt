package com.bangkit.tanikami_xml.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.bangkit.tanikami_xml.data.helper.Response
import com.bangkit.tanikami_xml.data.model.FakeDataSource
import com.bangkit.tanikami_xml.data.model.Product
import retrofit2.HttpException

class ProductRepository {

    fun getAllProduct(): LiveData<Response<List<Product>>> = liveData {
        emit(Response.Loading)
        try {
            emit(Response.Success(FakeDataSource.fakeDataProduct))
        } catch (e: HttpException) {
            Log.d("Repository", "getAllProduct: ${e.message}")
            emit(Response.Error(e.message.toString()))
        }
    }

}