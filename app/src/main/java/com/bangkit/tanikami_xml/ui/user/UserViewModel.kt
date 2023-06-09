package com.bangkit.tanikami_xml.ui.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.bangkit.tanikami_xml.data.helper.Response
import com.bangkit.tanikami_xml.data.remote.response.RegisterResponse
import com.bangkit.tanikami_xml.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.File
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepo: UserRepository
): ViewModel() {

    fun registerNewUser(
        imageProfile: File,
        id_ktp: String,
        name: String,
        email: String,
        password: String,
        telepon: String,
        alamat_regist: String,
        gender: Boolean,
        usia: Int,
        status: Boolean
    ): LiveData<Response<RegisterResponse>> {
        return userRepo.registerUser(imageProfile, id_ktp, name, email, password, telepon, alamat_regist, gender, usia, status)
    }
}