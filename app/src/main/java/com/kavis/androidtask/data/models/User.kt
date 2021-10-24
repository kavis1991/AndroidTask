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
    val gender: String?=null,
    val email: String?=null,
    val dateOfBirth: String?=null,
    val phone: String?=null,
    val picture: String?=null
){
    override fun equals(other: Any?): Boolean {
        return (other is User && other.id == this.id)
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + title.hashCode()
        result = 31 * result + firstName.hashCode()
        result = 31 * result + lastName.hashCode()
        result = 31 * result + (gender?.hashCode() ?: 0)
        result = 31 * result + (email?.hashCode() ?: 0)
        result = 31 * result + (dateOfBirth?.hashCode() ?: 0)
        result = 31 * result + (phone?.hashCode() ?: 0)
        result = 31 * result + (picture?.hashCode() ?: 0)
        return result
    }
}
