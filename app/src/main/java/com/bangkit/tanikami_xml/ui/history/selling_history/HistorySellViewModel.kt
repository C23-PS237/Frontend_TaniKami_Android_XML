package com.bangkit.tanikami_xml.ui.history.selling_history

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.bangkit.tanikami_xml.data.data_store.UserPreference
import com.bangkit.tanikami_xml.data.helper.Response
import com.bangkit.tanikami_xml.data.remote.response.ProductResponse
import com.bangkit.tanikami_xml.data.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HistorySellViewModel @Inject constructor(
    private val productRepo: ProductRepository,
    private val userPref: UserPreference
): ViewModel(){

    fun getProductbyIdKTP(id_ktp: String) : LiveData<Response<ProductResponse>> {
        return productRepo.getProductbyIdKTP(id_ktp)
    }

    fun getIdKtp() : LiveData<String> {
        return userPref.getIdKtp().asLiveData()
    }
}