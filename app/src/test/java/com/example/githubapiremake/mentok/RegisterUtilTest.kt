package com.example.githubapiremake.mentok

import com.example.githubapiremake.util.RegisterUtils
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test

class RegisterUtilTest {

    lateinit var regist : RegisterUtils

    @Before
    fun setup(){
        regist = RegisterUtils
    }

    @Test
    fun `name is empety`(){
        val validate = regist.validateUserRegister("","","")
        assertThat(validate == "success")
    }

    @Test
    fun `password must containts @`(){
        val validate = regist.validateUserRegister("Faizal","Faizal","aaaaaaaaaaa")
        assertThat(validate == "success")
    }

    @Test
    fun `password must be 6 digit`(){
        val validate = regist.validateUserRegister("Faizal","Faizal@gmail","aaa")
        assertThat(validate == "success")
    }
}