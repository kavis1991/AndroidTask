package com.kavis.androidtask.data.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.kavis.androidtask.data.local.UserDao
import com.kavis.androidtask.data.models.User
import com.kavis.androidtask.data.remote.RemoteDataSource
import com.kavis.androidtask.util.NetworkResult
import com.kavis.androidtask.util.performGetOperation
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject


class UserRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: UserDao
) {

    fun getUser(id: String): LiveData<NetworkResult<User>> = liveData(Dispatchers.IO) {
        emit(NetworkResult.loading())
        val responseStatus = remoteDataSource.getUser(id)
        if (responseStatus.status == NetworkResult.Status.SUCCESS) {
            emit(NetworkResult.success(responseStatus.data!!))

        } else if (responseStatus.status == NetworkResult.Status.ERROR) {
            emit(NetworkResult.error(responseStatus.message!!))
        }
    }

    fun getUsers(page: Int) = performGetOperation(
        databaseQuery = { localDataSource.getUsers() },
        networkCall = { remoteDataSource.getUsers(page) },
        saveCallResult = { localDataSource.insertAllUser(it.data) }
    )
}