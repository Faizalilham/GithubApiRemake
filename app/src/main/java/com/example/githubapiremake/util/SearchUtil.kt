package com.example.githubapiremake.util

object SearchUtil {

    fun validateSearch(name: String): String{
        if(name.isBlank()){
            return "Field cannot be empety"
        }
        return "success"

    }
}