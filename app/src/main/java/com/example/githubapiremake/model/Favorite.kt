package com.example.githubapiremake.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class Favorite(
    val login : String,
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val imageUrl : String,
    val html_url : String
): Parcelable