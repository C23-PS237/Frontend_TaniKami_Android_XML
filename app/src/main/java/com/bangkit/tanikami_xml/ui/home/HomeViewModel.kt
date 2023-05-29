package com.bangkit.tanikami_xml.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.bangkit.tanikami_xml.data.helper.Response
import com.bangkit.tanikami_xml.data.model.Product
import com.bangkit.tanikami_xml.data.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val productRepo: ProductRepository
): ViewModel(){

    fun getAllProducts() : LiveData<Response<List<Product>>> {
        return productRepo.getAllProduct()
    }
}