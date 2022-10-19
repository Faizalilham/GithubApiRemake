package com.example.githubapi.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.githubapiremake.api.ApiService
import com.example.githubapiremake.model.UserGithub
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowUserViewModel(application: Application): AndroidViewModel(application)  {
    val followers : MutableLiveData<ArrayList<UserGithub>?> = MutableLiveData()
    val following : MutableLiveData<ArrayList<UserGithub>?> = MutableLiveData()

    fun getFollowers(username : String){
        ApiService.ApiEndPoint().getFollowersUser(username)
            .enqueue(object : Callback<ArrayList<UserGithub>>{
                override fun onResponse(
                    call: Call<ArrayList<UserGithub>>,
                    response: Response<ArrayList<UserGithub>>
                ) {
                   if(response.isSuccessful){
                       val body = response.body()
                       if(body != null){
                           followers.postValue(body)
                       }
                   }
                }

                override fun onFailure(call: Call<ArrayList<UserGithub>>, t: Throwable) {
                    Log.d("ResponseError","${t.message}")
                }

            })
    }

    fun getFollowing(username : String){
        ApiService.ApiEndPoint().getFollowingUser(username)
            .enqueue(object : Callback<ArrayList<UserGithub>>{
                override fun onResponse(
                    call: Call<ArrayList<UserGithub>>,
                    response: Response<ArrayList<UserGithub>>
                ) {
                   if(response.isSuccessful){
                       val body = response.body()
                       if(body != null){
                           following.postValue(body)
                       }
                   }
                }

                override fun onFailure(call: Call<ArrayList<UserGithub>>, t: Throwable) {
                    Log.d("ResponseError","${t.message}")
                }

            })
    }
}