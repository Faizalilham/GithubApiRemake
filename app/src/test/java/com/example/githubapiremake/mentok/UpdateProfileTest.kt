package com.example.githubapiremake.mentok

import com.example.githubapiremake.util.UpdateProfile
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test

class UpdateProfileTest {

    lateinit var updateProfile : UpdateProfile

    @Before
    fun setup(){
        updateProfile = UpdateProfile
    }



    @Test
    fun `field is empety or blank` (){
        val validate = updateProfile.validateEditProfile(""," ")
        assertThat(validate == "success")
    }
}