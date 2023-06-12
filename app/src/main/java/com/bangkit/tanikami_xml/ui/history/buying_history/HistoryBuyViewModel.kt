package com.bangkit.tanikami_xml.ui.history.buying_history

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.bangkit.tanikami_xml.data.data_store.UserPreference
import com.bangkit.tanikami_xml.data.helper.Response
import com.bangkit.tanikami_xml.data.remote.response.GetBuyResponse
import com.bangkit.tanikami_xml.data.repository.BuyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HistoryBuyViewModel @Inject constructor(
    private val buyRepo: BuyRepository,
    private val userPref: UserPreference
): ViewModel(){
    fun getBuybyId(id_transaksi: String) : LiveData<Response<GetBuyResponse>> {
        return buyRepo.getBuybyId(id_transaksi)
    }
}