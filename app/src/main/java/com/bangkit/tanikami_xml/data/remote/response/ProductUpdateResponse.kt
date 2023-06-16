package com.bangkit.tanikami_xml.data.remote.response

import com.google.gson.annotations.SerializedName

data class ProductUpdateResponse(

	@field:SerializedName("payload")
	val payload: DataResponseUpdate,

	@field:SerializedName("message")
	val message: String
)

data class DataResponseUpdate(

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("isSuccess")
	val isSuccess: Int
)
