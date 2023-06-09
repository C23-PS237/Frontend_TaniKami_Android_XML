package com.bangkit.tanikami_xml.data.remote.response

import com.google.gson.annotations.SerializedName

data class ProductResponse(

	@field:SerializedName("payload")
	val payload: List<ProductItem>,

	@field:SerializedName("message")
	val message: String
)

data class ProductItem(

	@field:SerializedName("id_produk")
	val idProduk: Int,

	@field:SerializedName("besaran_stok")
	val besaranStok: String,

	@field:SerializedName("nama_produk")
	val namaProduk: String,

	@field:SerializedName("harga")
	val harga: Int,

	@field:SerializedName("url_gambar")
	val urlGambar: String,

	@field:SerializedName("rek_penjual")
	val rekPenjual: String,

	@field:SerializedName("id_ktp")
	val idKtp: String,

	@field:SerializedName("stok")
	val stok: Int,

	@field:SerializedName("deskripsi_produk")
	val deskripsiProduk: String,

	@field:SerializedName("nama_bank")
	val namaBank: String,

	@field:SerializedName("timestamp")
	val timestamp: String
)
