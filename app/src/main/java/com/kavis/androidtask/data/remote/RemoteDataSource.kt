package com.kavis.androidtask.data.remote

import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val userService: UserService): BaseDataSource() {
    suspend fun getUsers(limit: Int) = safeApiCall { userService.getUserList(limit)}
}