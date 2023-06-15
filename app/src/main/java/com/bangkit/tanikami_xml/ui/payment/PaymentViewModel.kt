package com.bangkit.tanikami_xml.ui.payment

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.bangkit.tanikami_xml.data.data_store.UserPreference
import com.bangkit.tanikami_xml.data.helper.Response
import com.bangkit.tanikami_xml.data.remote.response.BuyProductResponse
import com.bangkit.tanikami_xml.data.remote.response.DetailProductResponse
import com.bangkit.tanikami_xml.data.repository.BuyRepository
import com.bangkit.tanikami_xml.data.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class PaymentViewModel @Inject constructor(
    private val buyRepo: BuyRepository,
    private val productRepo: ProductRepository,
    private val userPref: UserPreference
): ViewModel() {

    fun buyProductNow(
        id_ktp: RequestBody,
        id_produk: RequestBody,
        alamat_penerima: RequestBody,
        harga: RequestBody,
        jumlah_dibeli: RequestBody,
        biaya_pengiriman: RequestBody,
        pajak: RequestBody,
        biaya_admin: RequestBody,
        biaya_total: RequestBody,
        status_pembayaran: RequestBody,
        status_pengiriman: RequestBody,
        id_penjual: RequestBody
    ): LiveData<Response<BuyProductResponse>> {
        return buyRepo.buyProduct(id_ktp, id_produk, alamat_penerima, harga, jumlah_dibeli, biaya_pengiriman, pajak, biaya_admin, biaya_total, status_pembayaran, status_pengiriman, id_penjual)
    }

    fun getDataProductById(id_produk: Int) : LiveData<Response<DetailProductResponse>> {
        return productRepo.getProductbyIdProduct(id_produk)
    }

    fun getAddress(): LiveData<String> {
        return userPref.getAddress().asLiveData()
    }

    fun getIdKtp(): LiveData<String> {
        return userPref.getIdKtp().asLiveData()
    }
}