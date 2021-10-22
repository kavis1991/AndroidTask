package com.kavis.androidtask.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kavis.androidtask.data.models.User

@Dao
interface UserDao {
    @Query("SELECT * FROM users")
    fun getUsers() : LiveData<List<User>>

    @Query("SELECT * FROM users WHERE id = :id")
    fun getUser(id: Int): LiveData<User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllUser(characters: List<User>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(character: User)
}
