package com.bangkit.tanikami_xml.ui.payment.uploading_bill

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.Response

class UploadingBillViewModel {

    private var _addBill = MutableLiveData<Response<UploadBillResponse>>()
    val addBill: LiveData<Response<UploadBillResponse>> = _addBill
}