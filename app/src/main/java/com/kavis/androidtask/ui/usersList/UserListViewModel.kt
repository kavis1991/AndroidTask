package com.kavis.androidtask.ui.usersList

import androidx.lifecycle.ViewModel
import com.kavis.androidtask.data.models.Data
import com.kavis.androidtask.data.models.User
import com.kavis.androidtask.data.repo.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(
    private val repository: UserRepository,
    private val scope: CoroutineScope
) : ViewModel() {

    private var userList: Data<User>? = null

    fun getUsers(connectivityAvailable: Boolean): Data<User>? {
        if (userList == null) {
            userList = repository.observePagedUser(connectivityAvailable, scope)
        }
        return userList
    }


    override fun onCleared() {
        super.onCleared()
        scope.cancel()
    }
}