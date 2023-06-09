package com.bangkit.tanikami_xml.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.bangkit.tanikami_xml.data.helper.Response
import com.bangkit.tanikami_xml.data.remote.response.ProductResponse
import com.bangkit.tanikami_xml.data.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailProductViewModel @Inject constructor(
    private val productRepo: ProductRepository
): ViewModel(){

    fun getProductbyIdProduct(idProduk: Int) : LiveData<Response<ProductResponse>> {
        return productRepo.getProductbyIdProduct(idProduk)
    }

}