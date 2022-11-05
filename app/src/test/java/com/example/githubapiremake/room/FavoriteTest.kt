//package com.example.githubapiremake.room
//
//import androidx.arch.core.executor.testing.InstantTaskExecutorRule
//import com.example.githubapiremake.model.Favorite
//import dagger.hilt.android.testing.HiltAndroidRule
//import dagger.hilt.android.testing.HiltAndroidTest
//import kotlinx.coroutines.ExperimentalCoroutinesApi
//import kotlinx.coroutines.test.runBlockingTest
//import org.junit.Before
//import org.junit.Rule
//import org.junit.Test
//import javax.inject.Inject
//import javax.inject.Named
//
//@ExperimentalCoroutinesApi
//@HiltAndroidTest
//class FavoriteTest {
//
//    @get:Rule
//    var hiltRule = HiltAndroidRule(this)
//
//    @get:Rule
//    val instant  = InstantTaskExecutorRule()
//
//
//    @Inject
//    @Named("test_db")
//    lateinit var db : SetupRoom
//    private lateinit var dao : DaoFavorite
//
//
//    @Before
//    fun init() {
//        hiltRule.inject()
//        dao = db.daoFavorite()
//    }
//
//
//    @Test
//    fun `insert data to room`() = runBlockingTest {
//        val favorite = Favorite("Faizal",0,"","")
//        dao.insertFavorite(favorite)
//    }
//
//    @Test
//    fun `delete data to room`() = runBlockingTest {
//        val favorite = Favorite("Faizal",0,"","")
//        dao.deleteUserFavorite(favorite)
//
//    }
//}