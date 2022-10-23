package com.example.githubapiremake.viewmodel

import androidx.lifecycle.ViewModel
import com.example.githubapiremake.model.Favorite
import com.example.githubapiremake.repository.FavoriteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(private val repository: FavoriteRepository):ViewModel() {

    fun allFavorite() = repository.getAllFavorite()
    fun allFavoriteObserver() = repository.getAllFavoriteObserver()


    fun checkUser(id : Int) = repository.checkUserFavorite(id)
    fun checkUserObserver() = repository.checkUserObserver()

    fun postUser(favorite: Favorite) = repository.postUser(favorite)
    fun postUserObserver() = repository.postUserFavoriteObserver()


    fun deleteUser(favorite: Favorite) = repository.deleteUser(favorite)
    fun deleteUserObserver() = repository.deleteUserFavoriteObserver()



}