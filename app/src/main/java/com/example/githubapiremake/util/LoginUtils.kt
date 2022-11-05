package com.example.githubapiremake.util

object LoginUtils {

    fun validateUserlogin( email: String, password: String): String {

        var result = ""
        if (email.isEmpty()){
            result =  "please enter email"
        } else {
            result =  "success"
        }
        if (!email.contains("@")){
          result = "please enter valid email"
        } else{
            result =  "success"
        }

        if (email.filter { it.isDigit() }.isEmpty()) {
            result = "email must contain at least one digit"
        }  else {
            result =  "success"
        }

        if(password.isEmpty()){
            result =  "please enter password"
        }  else {
            result =  "success"
        }

        if(password.length<=6) {
            result = "please enter valid password"
         }else {
            result = "success"
        }

        return result


    }

}