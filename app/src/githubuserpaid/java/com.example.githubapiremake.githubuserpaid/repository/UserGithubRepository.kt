package com.example.githubapiremake.githubuserpaid.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.githubapiremake.api.ApiEndPoint
import com.example.githubapiremake.model.ListResponse
import com.example.githubapiremake.model.UserGithub
import com.example.githubapiremake.model.UserGithubDetail
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Named

class UserGithubRepository @Inject constructor(private val api : ApiEndPoint) {

    private val datas : MutableLiveData<MutableList<UserGithub>> = MutableLiveData()
    private val detailDatas : MutableLiveData<UserGithubDetail?> = MutableLiveData()

    fun searchUserObserver() : LiveData<MutableList<UserGithub>> = datas
    fun detailUserObserver() : LiveData<UserGithubDetail?> = detailDatas


    fun searchUser(query : String){
        api.getDataUser(query)
            .enqueue(object : Callback<ListResponse<UserGithub>> {
                override fun onResponse(
                    call: Call<ListResponse<UserGithub>>,
                    response: Response<ListResponse<UserGithub>>
                ) {
                    if(response.isSuccessful){
                        val body = response.body()
                        if(body != null){
                            datas.postValue(body.items)
                        }
                    }else{
                        Log.d("Error", response.toString())
                    }
                }

                override fun onFailure(call: Call<ListResponse<UserGithub>>, t: Throwable) {
                    Log.d("ResponseError","${t.message}")
                }
            })
    }

    fun detailUser(username : String){
        api.getDetailUser(username)
            .enqueue(object : Callback<UserGithubDetail> {
                override fun onResponse(
                    call: Call<UserGithubDetail>,
                    response: Response<UserGithubDetail>
                ) {
                    if(response.isSuccessful){
                        val body = response.body()
                        if(body != null){
                            detailDatas.postValue(body)
                        }
                    }
                }

                override fun onFailure(call: Call<UserGithubDetail>, t: Throwable) {
                    Log.d("ResponseError","${t.message}")
                }
            })
    }

}