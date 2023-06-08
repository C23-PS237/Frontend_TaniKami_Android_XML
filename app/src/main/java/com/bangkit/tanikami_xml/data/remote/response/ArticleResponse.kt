package com.bangkit.tanikami_xml.data.remote.response

import com.google.gson.annotations.SerializedName

data class ArticleResponse(

	@field:SerializedName("payload")
	val payload: List<PayloadItem>,

	@field:SerializedName("message")
	val message: String
)

data class PayloadItem(

	@field:SerializedName("author")
	val author: String,

	@field:SerializedName("gambar_artikel")
	val gambarArtikel: String,

	@field:SerializedName("id_artikel")
	val idArtikel: Int,

	@field:SerializedName("judul")
	val judul: String,

	@field:SerializedName("body")
	val body: String,

	@field:SerializedName("timestamp")
	val timestamp: String
)
