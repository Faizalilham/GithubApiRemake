package com.example.githubapiremake.githubuserpaid.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.githubapiremake.util.Constant
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


private val Context.googleAuth : DataStore<Preferences> by preferencesDataStore(name = Constant.DS_AUTH_GOOGLE)
class GoogleSignInPreferences(@ApplicationContext private val context : Context) {
    private val accountKey = stringPreferencesKey(ACCOUNT)
    private val emailKey = stringPreferencesKey(EMAIL)
    private val nameKey = stringPreferencesKey(NAME)
    private val imageKey = stringPreferencesKey(IMAGE_URL)



    suspend fun setData(account : String,email :String,name: String,image :String){
        context.googleAuth.edit {
            it[accountKey] = account
            it[emailKey] = email
            it[nameKey] = name
            it[imageKey] = image
        }
    }

    fun getDataAccount() : Flow<String>{
        return context.googleAuth.data.map {
            it[emailKey] ?: "undefined"
        }
    }

    fun getDataEmail() : Flow<String>{
        return context.googleAuth.data.map {
            it[emailKey] ?: "undefined"
        }
    }

    fun getDataName() : Flow<String>{
        return context.googleAuth.data.map {
            it[nameKey] ?: "undefined"
        }
    }
    fun getDataImage() : Flow<String>{
        return context.googleAuth.data.map {
            it[imageKey] ?: "undefined"
        }
    }

    suspend fun deleteData(){
        context.googleAuth.edit {
            it.clear()
        }
    }



    companion object{
        private const val ACCOUNT = "account"
        private const val EMAIL = "email"
        private const val NAME = "name"
        private const val IMAGE_URL = "image_url"
    }

}