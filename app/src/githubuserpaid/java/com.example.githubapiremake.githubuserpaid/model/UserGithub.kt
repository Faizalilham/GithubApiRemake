package com.example.githubapiremake.githubuserpaid.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class UserGithub(
    val login: String,
    val id :Int,
    val avatar_url : String,
    val html_url : String
): Parcelable

data class SingleResponse<T>(
    val total_count :Int,
    val incomplete_result : Boolean,
    val items : T
)

data class SingleResponseAuth<T>(
    var msg : String,
    var status : Int,
    var data :T
)

data class ListResponse<T>(
    val total_count :Int,
    val incomplete_result : Boolean,
    val items : MutableList<T>
)
