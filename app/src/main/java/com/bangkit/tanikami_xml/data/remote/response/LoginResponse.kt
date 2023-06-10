package com.bangkit.tanikami_xml.data.remote.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(

	@field:SerializedName("payload")
	val payload: PItem,

	@field:SerializedName("message")
	val message: String
)

data class PItem(

	@field:SerializedName("usia")
	val usia: Int,

	@field:SerializedName("password")
	val password: String,

	@field:SerializedName("nama")
	val nama: String,

	@field:SerializedName("gender")
	val gender: Int,

	@field:SerializedName("profil")
	val profil: String,

	@field:SerializedName("telepon")
	val telepon: String,

	@field:SerializedName("alamat_penerima")
	val alamatPenerima: String,

	@field:SerializedName("id_ktp")
	val idKtp: String,

	@field:SerializedName("alamat_regist")
	val alamatRegist: String,

	@field:SerializedName("email")
	val email: String,

	@field:SerializedName("status")
	val status: Int
)
