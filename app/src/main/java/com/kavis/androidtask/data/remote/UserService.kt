package com.kavis.androidtask.data.remote

import com.kavis.androidtask.data.models.UsersList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface UserService {

    @GET("/data/v1//user")
    suspend fun getUserList(@Query("page") page: Int): Response<UsersList>
}