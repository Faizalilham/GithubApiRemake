package com.example.githubapiremake.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.githubapiremake.api.ApiEndPoint
import com.example.githubapiremake.model.UserGithub
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Named

class FollowUserRepository @Inject constructor(private val api : ApiEndPoint) {

    private val followers : MutableLiveData<ArrayList<UserGithub>?> = MutableLiveData()
    private val following : MutableLiveData<ArrayList<UserGithub>?> = MutableLiveData()

    fun followersObserver(): LiveData<ArrayList<UserGithub>?> = followers
    fun followingObserver(): LiveData<ArrayList<UserGithub>?> = following

    fun getFollowers(username : String){
        api.getFollowersUser(username)
            .enqueue(object : Callback<ArrayList<UserGithub>> {
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
        api.getFollowingUser(username)
            .enqueue(object : Callback<ArrayList<UserGithub>> {
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