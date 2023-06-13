package com.bangkit.tanikami_xml.ui.product.adding_editing

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.bangkit.tanikami_xml.data.data_store.UserPreference
import com.bangkit.tanikami_xml.data.helper.Response
import com.bangkit.tanikami_xml.data.remote.response.SellProductResponse
import com.bangkit.tanikami_xml.data.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class AddEditProductViewModel @Inject constructor(
    private  val userPref: UserPreference,
    private val productRepo: ProductRepository
): ViewModel(){

    fun getIdKtp() : LiveData<String> {
        return userPref.getIdKtp().asLiveData()
    }
    fun sellProduct(
        //idProduk: Int,
        besaran_stok: RequestBody,
        nama_produk:RequestBody,
        harga: RequestBody,
        gambar_produk: MultipartBody.Part,
        rek_penjual: RequestBody,
        id_ktp:RequestBody,
        stok: RequestBody,
        deskripsi_produk: RequestBody,
        nama_bank: RequestBody,
        //timestamp: String
        )
    : LiveData<Response<SellProductResponse>> {
        return productRepo.sellProduct(
            //idProduk,
            besaran_stok,
            nama_produk,
            harga,
            gambar_produk,
            rek_penjual,
            id_ktp,
            stok,
            deskripsi_produk,
            nama_bank
            //timestamp
        )
    }

}