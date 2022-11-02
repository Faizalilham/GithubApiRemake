package com.example.githubapiremake.githubuserpaid.model

data class UserGithubDetail (
    val login :String,
    val id : Int,
    val avatar_url : String,
    val html_url : String,
    val company : String,
    val location : String,
    val bio : String,
    val public_repos : Int,
    val followers : Int,
    val following : Int
)