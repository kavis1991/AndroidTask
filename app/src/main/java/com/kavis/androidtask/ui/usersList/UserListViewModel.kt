package com.kavis.androidtask.ui.usersList

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.kavis.androidtask.data.models.User
import com.kavis.androidtask.data.repo.UserRepository
import com.kavis.androidtask.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(
    private val repository: UserRepository
) : ViewModel() {

    //for testing
    var page = 0

    fun getUsers(): LiveData<NetworkResult<List<User>>> {
         return repository.getUsers(page)
    }
}