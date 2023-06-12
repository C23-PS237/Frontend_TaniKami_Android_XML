package com.bangkit.tanikami_xml.data.data_store

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserPreference @Inject constructor(private val dataStore: DataStore<Preferences>) {

    fun getUserInDataStore(): Flow<UserModel> {
        return dataStore.data.map { preferences ->
            UserModel(
                preferences[ID_KEY] ?: "",
                preferences[NAME_KEY] ?: "",
                preferences[PHONE_KEY] ?: "",
                preferences[ADDRESS_KEY] ?: "",
                preferences[IMAGE_KEY] ?: "",
                preferences[EMAIL_KEY] ?: "",
                preferences[PASSWORD_KEY] ?: "",
                preferences[STATE_KEY] ?: false
            )
        }
    }

    suspend fun saveUserToDataStore(user: UserModel) {
        dataStore.edit { pref ->
            pref[ID_KEY] = user.id_ktp
            pref[NAME_KEY] = user.name
            pref[PHONE_KEY] = user.phone
            pref[ADDRESS_KEY] = user.address
            pref[IMAGE_KEY] = user.image
            pref[EMAIL_KEY] = user.email
            pref[PASSWORD_KEY] = user.password
            pref[STATE_KEY] = user.isLogin
        }
    }

    suspend fun login() {
        dataStore.edit { pref ->
            pref[STATE_KEY] = true
        }
    }

    fun isLogin(): Flow<Boolean> {
        return dataStore.data.map {
            it[STATE_KEY] ?: false
        }
    }

    suspend fun logout() {
        dataStore.edit { pref ->
            pref[ID_KEY] = ""
            pref[NAME_KEY] = ""
            pref[PHONE_KEY] = ""
            pref[ADDRESS_KEY] = ""
            pref[IMAGE_KEY] = ""
            pref[EMAIL_KEY] = ""
            pref[PASSWORD_KEY] = ""
            pref[STATE_KEY] = false
        }
    }

    companion object {
        private val ID_KEY = stringPreferencesKey("id_ktp")
        private val NAME_KEY = stringPreferencesKey("name")
        private val PHONE_KEY = stringPreferencesKey("phone")
        private val ADDRESS_KEY = stringPreferencesKey("address")
        private val IMAGE_KEY = stringPreferencesKey("image")
        private val EMAIL_KEY = stringPreferencesKey("email")
        private val PASSWORD_KEY = stringPreferencesKey("password")
        private val STATE_KEY = booleanPreferencesKey("state")
    }
}