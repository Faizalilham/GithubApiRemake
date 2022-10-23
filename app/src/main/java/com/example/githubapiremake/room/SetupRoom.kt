package com.example.githubapiremake.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.githubapiremake.model.Favorite


@Database(
    entities = [Favorite::class],
    version = 1,
    exportSchema = false
)
abstract class SetupRoom : RoomDatabase() {
    abstract fun daoFavorite() : DaoFavorite
}