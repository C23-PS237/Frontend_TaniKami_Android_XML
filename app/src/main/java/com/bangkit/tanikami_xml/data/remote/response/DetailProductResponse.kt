package com.bangkit.tanikami_xml.data.remote.response

import com.google.gson.annotations.SerializedName

data class DetailProductResponse(

	@field:SerializedName("payload")
	val payload: Product,

	@field:SerializedName("message")
	val message: String
)

data class Product(

	@field:SerializedName("id_produk")
	val id_produk: Int,

	@field:SerializedName("besaran_stok")
	val besaran_stok: String,

	@field:SerializedName("nama_produk")
	val nama_produk: String,

	@field:SerializedName("harga")
	val harga: Int,

	@field:SerializedName("gambar_produk")
	val gambar_produk: String,

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
