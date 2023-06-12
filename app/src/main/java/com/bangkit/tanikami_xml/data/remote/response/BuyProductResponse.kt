package com.bangkit.tanikami_xml.data.remote.response

import com.google.gson.annotations.SerializedName

data class BuyProductResponse(

    @field:SerializedName("data")
    val data: Data,

    @field:SerializedName("message")
    val message: String
)