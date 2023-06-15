package com.bangkit.tanikami_xml.data.remote.response

import com.google.gson.annotations.SerializedName

data class ProductUpdateStockResponse(

	@field:SerializedName("payload")
	val payload: ItemPay,

	@field:SerializedName("message")
	val message: String
)

data class ItemPay(

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("isSuccess")
	val isSuccess: Int
)
