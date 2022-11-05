package com.example.githubapiremake.mentok

import com.example.githubapiremake.util.SearchUtil
import com.google.common.truth.Truth
import org.junit.Before
import org.junit.Test

class SearchUtilTest {
    lateinit var search : SearchUtil

    @Before
    fun setup(){
        search = SearchUtil
    }

    @Test
    fun `field is empety or blank` (){
        val validate = search.validateSearch("")
        Truth.assertThat(validate == "success")
    }
}