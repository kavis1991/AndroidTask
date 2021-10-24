package com.kavis.androidtask.data.models

data class UsersList (
    val data: List<User>,
    val total: Int,
    val page: Int,
    val limit: Int,
)
