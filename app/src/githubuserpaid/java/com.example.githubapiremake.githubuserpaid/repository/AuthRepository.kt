package com.example.githubapiremake.githubuserpaid.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.githubapiremake.api.ApiEndPoint
import com.example.githubapiremake.api.AuthEndPoint
import com.example.githubapiremake.model.ResponseUserLogin
import com.example.githubapiremake.model.ResponseUserRegister
import com.example.githubapiremake.model.UserLogin
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Named

class AuthRepository @Inject constructor(private val api : AuthEndPoint) {

    private val signUp : MutableLiveData<ResponseUserRegister?> = MutableLiveData()
    private val signIn : MutableLiveData<ResponseUserLogin?> = MutableLiveData()

    fun signUpObserver(): LiveData<ResponseUserRegister?> = signUp
    fun signInObserver(): LiveData<ResponseUserLogin?> = signIn


    fun doSignUp(name :String,email :String,password :String){
        api.doRegister(name,email,password)
            .enqueue(object : Callback<ResponseUserRegister> {
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
        api.doLogin(UserLogin(email,password))
            .enqueue(object : Callback<ResponseUserLogin> {
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
                        println("Not Success -> ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<ResponseUserLogin>, t: Throwable) {
                    signIn.postValue(null)
                    println("Server Error")
                }

            })
    }
}