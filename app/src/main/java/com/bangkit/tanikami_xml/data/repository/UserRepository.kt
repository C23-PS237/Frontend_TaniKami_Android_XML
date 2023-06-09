package com.bangkit.tanikami_xml.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.bangkit.tanikami_xml.data.data_store.UserModel
import com.bangkit.tanikami_xml.data.data_store.UserPreference
import com.bangkit.tanikami_xml.data.helper.Response
import com.bangkit.tanikami_xml.data.remote.response.RegisterResponse
import com.bangkit.tanikami_xml.data.remote.retrofit.ApiService
import com.bangkit.tanikami_xml.utils.Formatted.compressImage
import com.bangkit.tanikami_xml.utils.ImageProcessor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.HttpException
import java.io.File
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userPref: UserPreference,
    private val apiServ: ApiService
) {

    // Remote Repo syntax
    fun registerUser(
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
    ): LiveData<Response<RegisterResponse>> = liveData {
        emit(Response.Loading)

        val compressedImage = compressImage(imageProfile)
        val requestedImage = compressedImage.asRequestBody("image/jpeg".toMediaTypeOrNull())
        val imageMultiPart: MultipartBody.Part = MultipartBody.Part.createFormData(
            "profil",
            compressedImage.name,
            requestedImage
        )

        try {
            val response = apiServ.registerUser(imageMultiPart, id_ktp, name, email, password, telepon, alamat_regist, null, gender, usia, status)
            emit(Response.Success(response))
        } catch (e: HttpException) {
            Log.d(TAG, "registerUser: ${e.message()}")
            emit(Response.Error(e.message().toString()))
        }
    }

    // DataStore Repo Syntax
    fun getUserFromDataStore() = userPref.getUserInDataStore()
    suspend fun saveUserIntoDataStore(user: UserModel) = userPref.saveUserToDataStore(user)
    suspend fun loginDataStore() = userPref.login()
    suspend fun logoutDataStore() = userPref.logout()

    companion object {
        private const val TAG = "USER_REPOSITORY"
    }
}