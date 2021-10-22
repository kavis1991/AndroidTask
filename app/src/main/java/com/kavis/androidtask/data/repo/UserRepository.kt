package com.kavis.androidtask.data.repo

import com.kavis.androidtask.data.local.UserDao
import com.kavis.androidtask.data.remote.RemoteDataSource
import com.kavis.androidtask.util.performGetOperation
import javax.inject.Inject


class UserRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: UserDao
) {

    fun getUser(id: String) {

    }

    fun getUsers(page: Int) = performGetOperation(
        databaseQuery = { localDataSource.getUsers() },
        networkCall = { remoteDataSource.getUsers(page) },
        saveCallResult = { localDataSource.insertAllUser(it.data) }
    )
}