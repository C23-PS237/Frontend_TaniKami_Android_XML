package com.bangkit.tanikami_xml.data.remote.response

import com.google.gson.annotations.SerializedName

data class RegisterResponse(

	@field:SerializedName("payload")
	val payload: Payload,

	@field:SerializedName("message")
	val message: String
)

data class Payload(

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("isSuccess")
	val isSuccess: Int
)
