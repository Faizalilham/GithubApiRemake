package com.example.githubapiremake.githubuserpaid.model

data class Stories(
    val error : Boolean,
    val message : String,
    val  listStory : MutableList<Story>
)

data class Story(
    val id : String,
    val name : String,
    val description : String,
    val photoUrl : String,
    val createdAt : String,
    val lat : String,
    val lon : String
)