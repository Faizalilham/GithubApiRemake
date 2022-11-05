package com.example.githubapiremake.mentok

import com.example.githubapiremake.util.LoginUtils
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test

class LoginUtilTest {
    lateinit var login : LoginUtils

    @Before
    fun setUp() {
        login = LoginUtils
    }


    @Test
    fun email_is_empty() {
        val email = ""
        val assertLogin = login.validateUserlogin(email, "12345678")
        assertThat(assertLogin == "success")
    }

    @Test
    fun password_is_empty() {
        val password = ""
        val assertLogin = login.validateUserlogin("Faizalilham", password)
        assertThat(assertLogin == "success")
    }

    @Test
    fun `email not containts @`() {
        val assertLogin = login.validateUserlogin("Faizalilham", "asdfghjgf")
        assertThat(assertLogin == "success")
    }

    @Test
    fun `email ath least 1 digit`() {
        val assertLogin = login.validateUserlogin("F", "asdfghjgf")
        assertThat(assertLogin == "success")
    }

    @Test
    fun `passowrd minimal 6 digit`() {
        val assertLogin = login.validateUserlogin("Faizalilham", "asd")
        assertThat(assertLogin == "success")
    }

}