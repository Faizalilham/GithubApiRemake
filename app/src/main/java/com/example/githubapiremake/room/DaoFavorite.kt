package com.example.githubapiremake.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.githubapiremake.model.Favorite

@Dao
interface DaoFavorite {

    @Query("SELECT * FROM Favorite")
    fun getAllFavorite():MutableList<Favorite>

    @Insert
    fun insertFavorite(favorite: Favorite)

    @Query("SELECT count(*) FROM Favorite WHERE id= :id")
    suspend fun checkUserFavorite(id :Int):Int

    @Delete
    suspend fun deleteUserFavorite(favorite: Favorite)
}