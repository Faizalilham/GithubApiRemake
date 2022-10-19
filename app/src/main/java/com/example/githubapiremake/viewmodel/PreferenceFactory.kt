

package com.example.githubapiremake.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.githubapiremake.datastore.UserLoginPreferences


class PreferenceFactory(private val userLoginPreferences: UserLoginPreferences) :ViewModelProvider.NewInstanceFactory(){

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(AuthViewModel::class.java)){
            return AuthViewModel(userLoginPreferences) as T
        }

        throw IllegalArgumentException("Undefined class ${modelClass.name}")
    }

}