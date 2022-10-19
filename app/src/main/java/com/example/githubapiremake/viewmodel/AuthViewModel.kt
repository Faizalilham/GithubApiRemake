package com.example.githubapiremake.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.example.githubapiremake.api.ApiService
import com.example.githubapiremake.datastore.UserLoginPreferences
import com.example.githubapiremake.model.*
import com.example.githubapiremake.util.Constant
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthViewModel(private val userPreferences : UserLoginPreferences):ViewModel() {

    private val signUp : MutableLiveData<ResponseUserRegister?> = MutableLiveData()
    private val signIn : MutableLiveData<ResponseUserLogin?> = MutableLiveData()

    fun signUpObserver():MutableLiveData<ResponseUserRegister?> = signUp
    fun signInObserver():MutableLiveData<ResponseUserLogin?> = signIn

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


    fun doSignUp(name :String,email :String,password :String){
        ApiService.ApiAuth().doRegister(name,email,password)
            .enqueue(object : Callback<ResponseUserRegister>{
                override fun onResponse(
                    call: Call<ResponseUserRegister>,
                    response: Response<ResponseUserRegister>
                ) {
                    if(response.isSuccessful){
                        val body = response.body()
                        if(body != null){
                            signUp.postValue(body)

                        }else{
                            signUp.postValue(null)
                            println("Body Null")
                        }
                    }else{
                        signUp.postValue(null)
                        println("Not Success ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<ResponseUserRegister>, t: Throwable) {
                    signUp.postValue(null)
                    println("Server Error")
                }

            })
    }

    fun doSignIn(email :String,password :String){
        ApiService.ApiAuth().doLogin(UserLogin(email,password))
            .enqueue(object : Callback<ResponseUserLogin>{
                override fun onResponse(
                    call: Call<ResponseUserLogin>,
                    response: Response<ResponseUserLogin>
                ) {
                   if(response.isSuccessful){
                       val body = response.body()
                       if(body != null){
                           signIn.postValue(body)
                       }else{
                           signIn.postValue(null)
                           println("Body Null")
                       }

                   }else{
                       signIn.postValue(null)
                       println("Not Success")
                   }
                }

                override fun onFailure(call: Call<ResponseUserLogin>, t: Throwable) {
                    signIn.postValue(null)
                    println("Server Error")
                }

            })
    }

    fun getAllStories(token : String){
        ApiService.ApiAuth().getAllStories(token).enqueue(object : Callback<Stories>{
            override fun onResponse(call: Call<Stories>, response: Response<Stories>) {
               if(response.isSuccessful){
                   val body = response.body()
                   if(body != null){
                       println(body.listStory)
                       Log.d("STORY",body.listStory.toString())
                   }else{
                       Log.d("STORY","Body Null")
                   }
               }else{
                   Log.d("STORY",response.message())
               }
            }

            override fun onFailure(call: Call<Stories>, t: Throwable) {
                println(t.message)
            }

        })
    }
}