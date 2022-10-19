package com.example.githubapiremake.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.githubapiremake.util.Constant
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.userLoginAuth: DataStore<Preferences> by preferencesDataStore(name = Constant.DS_NAME)
class UserLoginPreferences(private val context  : Context) {

    private val tokenKey = stringPreferencesKey(Constant.TOKEN_KEY)

    suspend fun setToken(token :String){
        context.userLoginAuth.edit {
            it[tokenKey] = token
        }
    }

    fun getToken(): Flow<String> {
        return context.userLoginAuth.data.map {
            it[tokenKey] ?: "undefined"
        }
    }

    suspend fun deleteToken(){
        context.userLoginAuth.edit {
            it.clear()
        }
    }
}