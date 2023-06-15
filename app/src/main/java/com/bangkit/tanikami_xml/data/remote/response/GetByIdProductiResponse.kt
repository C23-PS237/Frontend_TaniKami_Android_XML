package com.bangkit.tanikami_xml.data.remote.response

import com.google.gson.annotations.SerializedName

data class GetByIdProductiResponse(

	@field:SerializedName("payload")
	val payload: List<ListProductPembelian>,

	@field:SerializedName("message")
	val message: String
)

data class ListProductPembelian(

	@field:SerializedName("id_produk")
	val idProduk: Int,

	@field:SerializedName("biaya_pengiriman")
	val biayaPengiriman: Int,

	@field:SerializedName("alamat_penerima")
	val alamatPenerima: String,

	@field:SerializedName("created_at")
	val createdAt: String,

	@field:SerializedName("id_transaksi")
	val idTransaksi: Int,

	@field:SerializedName("jumlah_dibeli")
	val jumlahDibeli: Int,

	@field:SerializedName("biaya_total")
	val biayaTotal: Int,

	@field:SerializedName("harga")
	val harga: Int,

	@field:SerializedName("pajak")
	val pajak: Int,

	@field:SerializedName("biaya_admin")
	val biayaAdmin: Int,

	@field:SerializedName("updated_at")
	val updatedAt: Any,

	@field:SerializedName("status_pembayaran")
	val statusPembayaran: Int,

	@field:SerializedName("status_pengiriman")
	val statusPengiriman: Int,

	@field:SerializedName("id_penjual")
	val idPenjual: Any,

	@field:SerializedName("id_ktp")
	val idKtp: String,

	@field:SerializedName("bukti_transfer")
	val buktiTransfer: String
)
