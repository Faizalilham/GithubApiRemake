package com.example.githubapiremake.util

object RegisterUtils {

    fun validateUserRegister(
        name: String,
        email: String,
        password: String
    ): Boolean {
        if (name.isEmpty() || email.isEmpty() || password.isEmpty()){
            return false
        }
        if (password.length <= 6){
            return false
        }
        return true
    }


}