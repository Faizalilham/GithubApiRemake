package com.example.githubapiremake.util

object RegisterUtils {

    fun validateUserRegister(
        name: String,
        email: String,
        password: String
    ): String {
        if (name.isEmpty() || email.isEmpty() || password.isEmpty()){
            return "Field cannot be empety"
        }
        if(!email.contains("@")){
            return "Error email"
        }
        if (password.length <= 6){
            return "Password must be at least 6 digit"
        }
        return "success"
    }


}