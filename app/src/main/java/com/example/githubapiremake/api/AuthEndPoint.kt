package com.example.githubapiremake.api


import com.example.githubapiremake.model.*
import retrofit2.Call
import retrofit2.http.*

interface AuthEndPoint {

    @FormUrlEncoded
    @POST("api/v1/user/register")
    fun doRegister(@Field("name")name :String,
                   @Field("email")email :String,
                   @Field("password")password :String):Call<ResponseUserRegister>

    @POST("api/v1/user/login")
    fun doLogin(@Body userLogin : UserLogin):Call<ResponseUserLogin>


    @GET("api/v1/user/current")
    fun getUserLogin(@Header("Authorization")authHeader : String):Call<ResponseUserCurrent>

    @PUT("api/v1/user/update")
    fun updateUserLogin(@Header("Authorization")auth: String,
                        @Body user : UserUpdate):Call<ResponseUserUpdate>


    @GET("stories")
    fun getAllStories(@Header("Authorization")authHeader : String): Call<Stories>


}