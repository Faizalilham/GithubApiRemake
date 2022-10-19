package com.example.githubapiremake.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


// REGISTER
data class ResponseUserRegister(
    val status : String,
    val data : User
)

data class User(
    val user : MutableList<DataUser>
)

data class DataUser(
    val id : Int,
    val code : String,
    val name : String,
    val email : String,
    val password : String,
    val createdAt : String,
    val updatedAt : String,
    val booking : MutableList<Any>
)

//LOGIN
data class UserLogin (
    val email : String,
    val password : String
)

data class ResponseUserLogin(
    val id : Int,
    val code : String,
    val name : String,
    val email : String,
    val token : String
)


// AFTER LOGIN
data class ResponseUserCurrent(
    val id : Int,
    val code : String,
    val name : String,
    val email : String,
    val password : String,
    val createdAt : String,
    val updatedAt : String
)

@Parcelize
data class UserUpdate (
    val name : String,
    val email : String,
    val password : String,
):Parcelable

data class ResponseUserUpdate(
    val message : String
)









//data class ResponseUserRegister(
//    val error : Boolean,
//    val message :String
//)

//data class ResponseUserLogin(
//    val error : Boolean,
//    val message :String,
//    val loginResult : LoginResult
//)
//
//data class LoginResult(
//    val userId : String,
//    val name : String,
//    val token :String
//)
