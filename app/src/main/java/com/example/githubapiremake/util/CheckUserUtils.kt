package com.example.githubapiremake.util

object CheckUserUtils {
    fun validateUser(token : String): Boolean{
        if(token.equals("undefined")){
            return false
        }
        return true

    }
}