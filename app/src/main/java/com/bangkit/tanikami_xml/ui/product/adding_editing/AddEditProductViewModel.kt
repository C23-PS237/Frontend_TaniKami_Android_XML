package com.bangkit.tanikami_xml.ui.product.adding_editing

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.bangkit.tanikami_xml.data.helper.Response
import com.bangkit.tanikami_xml.data.remote.response.SellProductResponse
import com.bangkit.tanikami_xml.data.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import okhttp3.MultipartBody
import javax.inject.Inject

@HiltViewModel
class AddEditProductViewModel @Inject constructor(
    private val productRepo: ProductRepository
): ViewModel(){

    fun sellProduct(
        //idProduk: Int,
        besaran_stok: String,
        nama_produk:String,
        harga: Int,
        url_gambar: MultipartBody.Part,
        rek_penjual: String,
        id_ktp:String,
        stok: Int,
        deskripsi_produk: String,
        nama_bank: String,
        //timestamp: String
        )
    : LiveData<Response<SellProductResponse>> {
        return productRepo.sellProduct(
            //idProduk,
            besaran_stok,
            nama_produk,
            harga,
            url_gambar,
            rek_penjual,
            id_ktp,
            stok,
            deskripsi_produk,
            nama_bank
            //timestamp
        )
    }

}