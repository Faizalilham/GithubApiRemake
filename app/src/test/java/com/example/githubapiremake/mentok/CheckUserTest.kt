package com.example.githubapiremake.mentok

import com.example.githubapiremake.util.CheckUserUtils
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test

class CheckUserTest {

    lateinit var user : CheckUserUtils

    @Before
    fun setup(){
        user = CheckUserUtils
    }

    @Test
    fun `check token not undefined`(){
        val validate = CheckUserUtils.validateUser("undefined")
        assertThat(validate)
    }
}