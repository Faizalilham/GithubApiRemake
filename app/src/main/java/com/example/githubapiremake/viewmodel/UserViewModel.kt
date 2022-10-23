package com.example.githubapiremake.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubapiremake.model.ResponseUserCurrent
import com.example.githubapiremake.model.ResponseUserUpdate
import com.example.githubapiremake.model.UserUpdate
import com.example.githubapiremake.repository.UserLoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class UserViewModel @Inject constructor(private val repository: UserLoginRepository):ViewModel() {

    fun currentUser(token : String) = repository.getCurrentUser(token)
    fun getCurrentUserObserver() = repository.getCurrentUserObserve()

    fun updateUser(token :String,
                   name:String,
                   email :String,
                   password : String) = repository.updateCurrentUser(token,name,email,password)

    fun getUpdateUserObserver() = repository.updateCurrentUserObserve()



}