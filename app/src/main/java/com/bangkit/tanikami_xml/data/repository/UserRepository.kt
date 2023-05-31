package com.bangkit.tanikami_xml.data.repository

import com.bangkit.tanikami_xml.data.data_store.UserModel
import com.bangkit.tanikami_xml.data.data_store.UserPreference
import javax.inject.Inject

class UserRepository @Inject constructor(private val userPref: UserPreference) {

    // Remote Repo syntax

    // DataStore Repo Syntax
    fun getUserFromDataStore() = userPref.getUserInDataStore()
    suspend fun saveUserIntoDataStore(user: UserModel) = userPref.saveUserToDataStore(user)
    suspend fun loginDataStore() = userPref.login()
    suspend fun logoutDataStore() = userPref.logout()
}