package com.example.githubapiremake.githubuserpaid.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.githubapiremake.model.Favorite
import com.example.githubapiremake.room.DaoFavorite
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavoriteRepository @Inject constructor(private val fav : DaoFavorite) {


    private val getAllFavorite : MutableLiveData<MutableList<Favorite>> = MutableLiveData()
    fun getAllFavoriteObserver() : LiveData<MutableList<Favorite>> = getAllFavorite


    private val postUserFavorite : MutableLiveData<Unit> = MutableLiveData()
    fun postUserFavoriteObserver() : LiveData<Unit> = postUserFavorite

    private val deleteUserFavorite : MutableLiveData<Unit> = MutableLiveData()
    fun deleteUserFavoriteObserver() : LiveData<Unit> = deleteUserFavorite


    private val checkUser : MutableLiveData<Int> = MutableLiveData()
    fun checkUserObserver() : LiveData<Int> = checkUser


    fun getAllFavorite(){
        CoroutineScope(Dispatchers.IO).launch {
            getAllFavorite.postValue(fav.getAllFavorite())
        }
    }

    fun postUser(favorite: Favorite){
        CoroutineScope(Dispatchers.IO).launch {
            postUserFavorite.postValue(fav.insertFavorite(favorite))
        }
    }

    fun deleteUser(favorite: Favorite){
        CoroutineScope(Dispatchers.IO).launch {
            deleteUserFavorite.postValue(fav.deleteUserFavorite(favorite))
        }
    }

    fun checkUserFavorite(id :Int){
        CoroutineScope(Dispatchers.IO).launch {
            checkUser.postValue(fav.checkUserFavorite(id))
        }
    }







}