package com.bangkit.tanikami_xml.data.data_store

data class UserModel(
    val id_ktp: Int,
    val name: String,
    val phone: String,
    val address: String,
    val isLogin: Boolean
)
