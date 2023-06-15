package com.bangkit.tanikami_xml.ui.history.buying_history

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.bangkit.tanikami_xml.data.data_store.UserPreference
import com.bangkit.tanikami_xml.data.helper.Response
import com.bangkit.tanikami_xml.data.remote.response.DetailProductResponse
import com.bangkit.tanikami_xml.data.remote.response.GetBuyResponse
import com.bangkit.tanikami_xml.data.remote.response.Product
import com.bangkit.tanikami_xml.data.repository.BuyRepository
import com.bangkit.tanikami_xml.data.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HistoryBuyViewModel @Inject constructor(
    private val productRepo: ProductRepository,
    private val buyRepo: BuyRepository,
    private val userPref: UserPreference
): ViewModel(){

    fun getIdKtp() : LiveData<String> {
        return userPref.getIdKtp().asLiveData()
    }
    fun getBuybyIdKtp(id_ktp: String) : LiveData<Response<GetBuyResponse>> {
        return buyRepo.getBuybyIdKtp(id_ktp)
    }

    fun getProductbyIdProduct(id_produk: Int) : LiveData<Response<DetailProductResponse>> {
        return productRepo.getProductbyIdProduct(id_produk)
    }

    fun getListProductsByIdProducts(id_products: List<Int>): LiveData<List<Product>> {
        return buyRepo.getListDataBuyByIdProducts(id_products)
    }
}