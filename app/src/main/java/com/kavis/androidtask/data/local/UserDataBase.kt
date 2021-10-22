package com.kavis.androidtask.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.kavis.androidtask.data.models.User

@Database(entities = [User::class], version = 5, exportSchema = false)
abstract class UserDataBase : RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {
        @Volatile private var instance: UserDataBase? = null

        fun getDatabase(context: Context): UserDataBase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also {
                    instance = it
                }
            }
        }

        private fun buildDatabase(appContext: Context) =
            Room.inMemoryDatabaseBuilder(appContext, UserDataBase::class.java)
                .fallbackToDestructiveMigration()
                .build()

    }

}