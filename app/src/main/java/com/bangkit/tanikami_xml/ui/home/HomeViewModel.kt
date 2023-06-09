package com.bangkit.tanikami_xml.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.bangkit.tanikami_xml.data.helper.Response
import com.bangkit.tanikami_xml.data.remote.response.ProductResponse
import com.bangkit.tanikami_xml.data.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val productRepo: ProductRepository
): ViewModel(){

    fun getAllProducts() : LiveData<Response<ProductResponse>> {
        return productRepo.getAllProducts()
    }

}