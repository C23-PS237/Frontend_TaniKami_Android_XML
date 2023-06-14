package com.bangkit.tanikami_xml.ui.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.bangkit.tanikami_xml.data.data_store.UserModel
import com.bangkit.tanikami_xml.data.helper.Response
import com.bangkit.tanikami_xml.data.remote.response.PItem
import com.bangkit.tanikami_xml.data.remote.response.RegisterResponse
import com.bangkit.tanikami_xml.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.RequestBody
import java.io.File
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepo: UserRepository
): ViewModel() {

    fun registerNewUser(
        imageProfile: File,
        id_ktp: RequestBody,
        name: RequestBody,
        email: RequestBody,
        password: RequestBody,
        telepon: RequestBody,
        alamat_regist: RequestBody,
        gender: RequestBody,
        usia: RequestBody,
        status: RequestBody
    ): LiveData<Response<RegisterResponse>> {
        return userRepo.registerUser(imageProfile, id_ktp, name, email, password, telepon, alamat_regist, gender, usia, status)
    }

    fun loginUser(idKtp: String): LiveData<Response<PItem>> {
        return userRepo.loginUser(idKtp)
    }

    fun getDataFromDataStore(): LiveData<UserModel> {
        return userRepo.getUserFromDataStore().asLiveData()
    }

    fun saveUserToDataStore(user: UserModel) {
        viewModelScope.launch {
            userRepo.saveUserIntoDataStore(user)
        }
    }

    fun logoutDeleteDataStore() {
        viewModelScope.launch {
            userRepo.logoutDataStore()
        }
    }
}