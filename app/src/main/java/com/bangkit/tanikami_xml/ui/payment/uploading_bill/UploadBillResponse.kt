package com.bangkit.tanikami_xml.ui.payment.uploading_bill

import com.google.gson.annotations.SerializedName

data class UploadBillResponse(

    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String
)