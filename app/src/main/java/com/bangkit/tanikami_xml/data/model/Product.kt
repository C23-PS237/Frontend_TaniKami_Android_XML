package com.bangkit.tanikami_xml.data.model

data class Product (
    val id_product: Int,
    val id_ktp: Int,
    val nama_product: String,
    val besaran_stok: String,
    val stok: Int,
    val harga: Int,
    val url_gambar: String,
    val deskripsi_product: String,
    val nama_bank: String,
    val rek_penjual: String,
)