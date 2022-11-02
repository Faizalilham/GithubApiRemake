package com.example.githubapiremake.githubuserpaid.viewmodel


import androidx.lifecycle.ViewModel
import com.example.githubapiremake.repository.FollowUserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class FollowUserViewModel @Inject constructor(private val repository: FollowUserRepository): ViewModel()  {

    fun getUserFollowers(username :String) = repository.getFollowers(username)
    fun followersObserver() = repository.followersObserver()

    fun getUserFollowing(username :String) = repository.getFollowing(username)
    fun followingObserver() = repository.followingObserver()

}