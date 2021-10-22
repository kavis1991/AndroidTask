package com.kavis.androidtask.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "users")
data class User(
    @PrimaryKey
    val id: String,
    val title: String,
    val firstName: String,
    val lastName: String,
    val gender: String?,
    val email: String?,
    val dateOfBirth: String?,
    val phone: String?,
    val picture: String?
)
