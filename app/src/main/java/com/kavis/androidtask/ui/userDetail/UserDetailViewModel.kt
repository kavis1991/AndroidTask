package com.kavis.androidtask.ui.userDetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.kavis.androidtask.data.models.User
import com.kavis.androidtask.data.repo.UserRepository
import com.kavis.androidtask.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserDetailViewModel  @Inject constructor(
    private val repository: UserRepository
) : ViewModel() {

    private val _id = MutableLiveData<String>()

    private val _user = _id.switchMap { id ->
        repository.getUser(id)
    }

    val user: LiveData<NetworkResult<User>> = _user

    fun start(id: String) {
        _id.value = id
    }
}