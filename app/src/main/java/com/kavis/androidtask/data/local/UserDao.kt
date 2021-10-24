package com.kavis.androidtask.data.local

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
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
    fun getUser(id: String): LiveData<User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllUser(userList: List<User>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)

    @Query("SELECT * FROM users")
    fun getPagedUser(): DataSource.Factory<Int, User>
}
