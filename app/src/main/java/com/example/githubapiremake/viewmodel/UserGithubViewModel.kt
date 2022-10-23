package com.example.githubapi.viewmodel


import androidx.lifecycle.ViewModel
import com.example.githubapiremake.repository.UserGithubRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import javax.inject.Named


@HiltViewModel
class UserGithubViewModel @Inject constructor(private val repository : UserGithubRepository): ViewModel() {

      fun searchUser(query : String) = repository.searchUser(query)
      fun searchUserObserver() = repository.searchUserObserver()


    fun detailUser(username :String) = repository.detailUser(username)
    fun detailUserObserver() = repository.detailUserObserver()






}