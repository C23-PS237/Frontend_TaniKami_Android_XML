package com.bangkit.tanikami_xml.data.remote.response

import com.google.gson.annotations.SerializedName

data class ArtikelDetaiilResponse(

	@field:SerializedName("payload")
	val payload: PayloadItem,

	@field:SerializedName("message")
	val message: String
)