package com.bangkit.tanikami_xml.data.remote.response

import com.google.gson.annotations.SerializedName

data class ProductResponse(

	@field:SerializedName("payload")
	val payload: List<ProductItem>,

	@field:SerializedName("message")
	val message: String
)

data class ProductItem(

	//@PrimaryKey(autoGenerate = true)
	@field:SerializedName("id_produk")
	val id_produk: Int,

	@field:SerializedName("besaran_stok")
	val besaran_stok: String,

	@field:SerializedName("nama_produk")
	val nama_produk: String,

	@field:SerializedName("harga")
	val harga: Int,

	@field:SerializedName("url_gambar")
	val url_gambar: String,

	@field:SerializedName("rek_penjual")
	val rek_penjual: String,

	@field:SerializedName("id_ktp")
	val id_ktp: String,

	@field:SerializedName("stok")
	val stok: Int,

	@field:SerializedName("deskripsi_produk")
	val deskripsi_produk: String,

	@field:SerializedName("nama_bank")
	val nama_bank: String,

	@field:SerializedName("timestamp")
	val timestamp: String
)
