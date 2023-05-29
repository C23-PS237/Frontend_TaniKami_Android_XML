package com.bangkit.tanikami_xml.data.model

data class pembelian(
    val id_transaksi: Int,
    val id_produk: Int,
    val alamat_penerima: String,
    val harga: Int,
    val jumlah_dibeli: Int,
    val biaya_pengiriman: Int,
    val pajak: Int,
    val biaya_admin: Int,
    val biaya_total: Int,
    val status_pembayaran: Boolean,
    val status_pengiriman: Boolean,
    val bukti_transfer: String,
)
