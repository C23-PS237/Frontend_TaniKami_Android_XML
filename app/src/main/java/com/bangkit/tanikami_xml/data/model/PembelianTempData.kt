package com.bangkit.tanikami_xml.data.model

import okhttp3.RequestBody

data class PembelianTempData(
    val id_ktp: RequestBody,
    val id_product: RequestBody,
    val alamat_penerima: RequestBody,
    val harga: RequestBody,
    val jumlah_beli: RequestBody,
    val biaya_pengiriman: RequestBody,
    val pajak: RequestBody,
    val biaya_admin: RequestBody,
    val biaya_total: RequestBody,
    val status_pembayaran: RequestBody,
    val status_pengiriman: RequestBody,
    val id_penjual: RequestBody
)
