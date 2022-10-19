package com.example.githubapiremake.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubapiremake.api.ApiService
import com.example.githubapiremake.model.ResponseUserCurrent
import com.example.githubapiremake.model.ResponseUserUpdate
import com.example.githubapiremake.model.UserUpdate
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserViewModel:ViewModel() {

    private val getCurrentUser : MutableLiveData<ResponseUserCurrent?> = MutableLiveData()
    private val updateCurrentUser : MutableLiveData<ResponseUserUpdate?> = MutableLiveData()

    fun getCurrentUserObserve() :MutableLiveData<ResponseUserCurrent?> = getCurrentUser
    fun updateCurrentUserObserve() :MutableLiveData<ResponseUserUpdate?> = updateCurrentUser

    fun getCurrentUser(token : String){
        ApiService.ApiAuth().getUserLogin(token)
            .enqueue(object : Callback<ResponseUserCurrent>{
                override fun onResponse(
                    call: Call<ResponseUserCurrent>,
                    response: Response<ResponseUserCurrent>
                ) {
                    if(response.isSuccessful){
                        val body = response.body()
                        if(body != null){
                            getCurrentUser.postValue(body)
                        }else{
                            getCurrentUser.postValue(null)
                            Log.d("CURRENT_USER","Null")
                        }
                    }else{
                        getCurrentUser.postValue(null)
                        Log.d("CURRENT_USER",response.message())
                    }
                }

                override fun onFailure(call: Call<ResponseUserCurrent>, t: Throwable) {
                    getCurrentUser.postValue(null)
                    Log.d("CURRENT_USER","onFailure")
                }

            })
    }

    fun updateCurrentUser(token :String,name:String,email :String, password : String){
        ApiService.ApiAuth().updateUserLogin(token,UserUpdate(name,email,password))
            .enqueue(object : Callback<ResponseUserUpdate>{
                override fun onResponse(
                    call: Call<ResponseUserUpdate>,
                    response: Response<ResponseUserUpdate>
                ) {

                    if(response.isSuccessful){
                        val body = response.body()
                        if(body != null){
                            updateCurrentUser.postValue(body)
                        }else{
                            updateCurrentUser.postValue(null)
                            Log.d("CURRENT_USER","Null")
                        }
                    }else{
                        updateCurrentUser.postValue(null)
                        Log.d("CURRENT_USER",response.message())
                    }
                }

                override fun onFailure(call: Call<ResponseUserUpdate>, t: Throwable) {
                    updateCurrentUser.postValue(null)
                    Log.d("CURRENT_USER","onFailure")
                }

            })
    }
}