package com.bangkit.tanikami_xml.data.data_store

data class UserModel(
    val id_ktp: String,
    val name: String,
    val phone: String,
    val address: String,
    val image: String,
    val isLogin: Boolean
)
