package com.example.githubapiremake.githubuserpaid.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import com.example.githubapiremake.datastore.GoogleSignInPreferences
import com.example.githubapiremake.datastore.UserLoginPreferences
import com.example.githubapiremake.model.*
import com.example.githubapiremake.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Named


@HiltViewModel
class AuthViewModel @Inject constructor(
    @ApplicationContext context : Context,
    private val repository : AuthRepository) :ViewModel() {

    private val userPreferences = UserLoginPreferences(context)
    private var  googleAuth = GoogleSignInPreferences(context)


    fun setData(account :String,email :String,name :String,image : String){
        viewModelScope.launch {
            googleAuth.setData(account,email, name,image)
        }
    }

    fun getDataAccount() :LiveData<String> = googleAuth.getDataAccount().asLiveData()
    fun getDataEmail() :LiveData<String> = googleAuth.getDataEmail().asLiveData()
    fun getDataName() :LiveData<String> = googleAuth.getDataName().asLiveData()
    fun getDataImage() :LiveData<String> = googleAuth.getDataImage().asLiveData()

    fun deleteData(){
        viewModelScope.launch {
            googleAuth.deleteData()
        }
    }


    fun setToken(token : String){
        viewModelScope.launch {
            userPreferences.setToken(token)
        }
    }

    fun deleteToken(){
        viewModelScope.launch {
            userPreferences.deleteToken()
        }
    }

    fun getToken(): LiveData<String> = userPreferences.getToken().asLiveData()

    fun doRegister(name :String,email :String,password :String) = repository.doSignUp(name,email,password)
    fun registerObserver() = repository.signUpObserver()

    fun doLogin(email :String,password : String) = repository.doSignIn(email,password)
    fun loginObserver() = repository.signInObserver()

}