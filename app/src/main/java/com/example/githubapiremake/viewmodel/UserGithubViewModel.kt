package com.example.githubapi.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.githubapiremake.api.ApiService
import com.example.githubapiremake.model.ListResponse
import com.example.githubapiremake.model.UserGithub
import com.example.githubapiremake.model.UserGithubDetail
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserGithubViewModel(application: Application): AndroidViewModel(application) {

    val datas : MutableLiveData<MutableList<UserGithub>> = MutableLiveData()
    val detailDatas :  MutableLiveData<UserGithubDetail?> = MutableLiveData()


    fun searchUser(query : String){
        ApiService.ApiEndPoint().getDataUser(query)
            .enqueue(object : Callback<ListResponse<UserGithub>>{
                override fun onResponse(
                    call: Call<ListResponse<UserGithub>>,
                    response: Response<ListResponse<UserGithub>>
                ) {
                    if(response.isSuccessful){
                        val body = response.body()
                        if(body != null){
                            datas.postValue(body.items)
                        }
                    }
                }

                override fun onFailure(call: Call<ListResponse<UserGithub>>, t: Throwable) {
                    Log.d("ResponseError","${t.message}")
                }
            })
    }

    fun detailUser(username : String){
        ApiService.ApiEndPoint().getDetailUser(username)
            .enqueue(object : Callback<UserGithubDetail>{
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