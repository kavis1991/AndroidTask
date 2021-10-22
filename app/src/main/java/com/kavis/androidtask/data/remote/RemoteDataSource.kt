package com.kavis.androidtask.data.remote

import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val userService: UserService): BaseDataSource() {
    suspend fun getUsers(page: Int) = safeApiCall { userService.getUserList(page)}
    suspend fun getUser(id: String) = safeApiCall { userService.getUser(id) }
}