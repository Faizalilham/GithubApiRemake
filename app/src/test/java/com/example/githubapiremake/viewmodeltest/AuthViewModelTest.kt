//package com.example.githubapiremake.viewmodeltest
//
//import com.example.githubapiremake.api.ApiEndPoint
//import dagger.hilt.android.testing.HiltAndroidRule
//import dagger.hilt.android.testing.HiltAndroidTest
//
//import org.hamcrest.MatcherAssert.assertThat
//import org.junit.Before
//import org.junit.Rule
//import org.junit.Test
//import javax.inject.Inject
//import javax.inject.Named
//
////import org.junit.jupiter.api.Assertions.*
//
//@HiltAndroidTest
//class AuthViewModelTest {
//
//    @get:Rule
//    var hiltRule = HiltAndroidRule(this)
//
//
//    @Inject
//    lateinit var api : ApiEndPoint
//
//
//    @Before
//    fun init() {
//        hiltRule.inject()
//    }
//
//
//   @Test
//   fun `github testing`(){
//       val apis = api.getDataUser("Faizal").execute()
//       assertThat(apis.code().toString(),apis.code() == 200)
//   }
//
//
//}