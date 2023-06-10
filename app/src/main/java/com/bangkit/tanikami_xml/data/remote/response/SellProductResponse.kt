package com.bangkit.tanikami_xml.data.remote.response

import com.google.gson.annotations.SerializedName

data class SellProductResponse(

	@field:SerializedName("data")
	val data: Data,

	@field:SerializedName("message")
	val message: String
)

data class Data(

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("isSuccess")
	val isSuccess: Boolean
)
