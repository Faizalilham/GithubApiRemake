package com.example.githubapiremake.api

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(
    private val tokenType :String,
    private val accessToken : String
):Interceptor

{
    override fun intercept(chain: Interceptor.Chain): Response {
        var req = chain.request()
        req = req.newBuilder().header("Authorization","$tokenType $accessToken").build()
        return chain.proceed(req)
    }


}