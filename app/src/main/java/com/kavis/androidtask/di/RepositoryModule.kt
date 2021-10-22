package com.kavis.androidtask.di

import android.content.Context
import com.kavis.androidtask.UserApplication
import com.kavis.androidtask.data.local.UserDao
import com.kavis.androidtask.data.local.UserDataBase
import com.kavis.androidtask.data.remote.RemoteDataSource
import com.kavis.androidtask.data.remote.UserService
import com.kavis.androidtask.data.repo.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideUserService(retrofit: Retrofit): UserService = retrofit.create(UserService::class.java)

    @Singleton
    @Provides
    fun provideUserRemoteDataSource(userService: UserService) = RemoteDataSource(userService = userService)

    @Singleton
    @Provides
    fun provideUserDatabase(app: UserApplication) = UserDataBase.getDatabase(app)

    @Singleton
    @Provides
    fun provideUserDao(db: UserDataBase) = db.userDao()

    @Singleton
    @Provides
    fun provideUserRepository(remoteDataSource: RemoteDataSource,
                          localDataSource: UserDao) =
        UserRepository(remoteDataSource, localDataSource)
}