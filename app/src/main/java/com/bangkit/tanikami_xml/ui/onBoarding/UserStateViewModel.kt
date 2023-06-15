package com.bangkit.tanikami_xml.ui.onBoarding

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.bangkit.tanikami_xml.data.data_store.UserModel
import com.bangkit.tanikami_xml.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserStateViewModel @Inject constructor(
    private val userRepo: UserRepository
): ViewModel() {

    fun isLogin(): LiveData<UserModel> {
        return userRepo.getUserFromDataStore().asLiveData()
    }

    fun logout() {
        viewModelScope.launch {
            userRepo.logoutDataStore()
        }
    }
}