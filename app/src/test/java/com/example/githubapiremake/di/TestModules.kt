package com.example.githubapiremake.di

import android.content.Context
import androidx.room.Room
import com.example.githubapiremake.room.DaoFavorite
import com.example.githubapiremake.room.SetupRoom
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TestModules {

    @Provides
    @Named("test_db")
    fun roomProvides(@ApplicationContext context : Context) =
        Room.inMemoryDatabaseBuilder(context,SetupRoom::class.java)
            .allowMainThreadQueries()
            .build()


}