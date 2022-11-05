package com.example.githubapiremake.api

import com.example.githubapiremake.model.ListResponse
import com.example.githubapiremake.model.UserGithub
import com.example.githubapiremake.model.UserGithubDetail
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiEndPoint {

    @GET("search/users")
    @Headers("Authorization: token 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")
    fun getDataUser(@Query("q")query : String): Call<ListResponse<UserGithub>>

    @GET("users/{username}")
    @Headers("Authorization: token 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")
    fun getDetailUser(@Path("username")username:String): Call<UserGithubDetail>

    @GET("users/{username}/followers")
    @Headers("Authorization: token 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")
    fun getFollowersUser(@Path("username")username :String): Call<ArrayList<UserGithub>>

    @GET("users/{username}/following")
    @Headers("Authorization: token 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")
    fun getFollowingUser(@Path("username")username :String): Call<ArrayList<UserGithub>>
}

