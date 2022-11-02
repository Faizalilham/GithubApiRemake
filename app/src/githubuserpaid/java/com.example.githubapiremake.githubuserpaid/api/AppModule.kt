package com.example.githubapiremake.githubuserpaid.api

import android.content.Context
import androidx.room.Room
import com.example.githubapiremake.room.DaoFavorite
import com.example.githubapiremake.room.SetupRoom
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton


//@Module
//@InstallIn(SingletonComponent::class)
//object AppModules {
//    private const val BASE_URL_AUTH = "https://api-resto-auth.herokuapp.com/" // OAUTH WITH BEARER TOKEN
//    private const val BASE_URL = "https://api.github.com/"
//
//    @Provides
//    @Singleton
//    fun okHttpClient():OkHttpClient{
//        return OkHttpClient.Builder()
//            .connectTimeout(30,TimeUnit.SECONDS)
//            .readTimeout(30,TimeUnit.SECONDS)
//            .writeTimeout(30,TimeUnit.SECONDS)
//            .build()
//    }
//
//
//    @Singleton
//    @Provides
//    @Named("auths")
//    fun setupRetrofitAuth(okHttp : OkHttpClient):Retrofit{
//        return Retrofit.Builder()
//            .baseUrl(BASE_URL_AUTH)
//            .client(okHttp)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//    }
//
//    @Singleton
//    @Provides
//    @Named("githubs")
//    fun setupRetrofitGithub(okHttp : OkHttpClient):Retrofit{
//        return Retrofit.Builder()
//            .baseUrl(BASE_URL)
//            .client(okHttp)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//    }
//
//
//    @Provides
//    fun apiEndPoint(@Named("githubs") retrofit : Retrofit):ApiEndPoint = retrofit.create(ApiEndPoint::class.java)
//
//    @Provides
//    fun authEndPoint(@Named("auths") retrofit : Retrofit):AuthEndPoint = retrofit.create(AuthEndPoint::class.java)
//
//    @Provides
//    fun roomProvides(@ApplicationContext context : Context):SetupRoom{
//        return Room.databaseBuilder(context,SetupRoom::class.java,"DBFavorite").build()
//    }
//
//    @Singleton
//    @Provides
//    fun daoFavorite(db : SetupRoom):DaoFavorite = db.daoFavorite()
//}