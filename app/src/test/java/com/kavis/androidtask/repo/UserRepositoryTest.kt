package com.kavis.androidtask.repo

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.kavis.androidtask.data.local.UserDao
import com.kavis.androidtask.data.local.UserDataBase
import com.kavis.androidtask.data.remote.RemoteDataSource
import com.kavis.androidtask.data.remote.UserService
import com.kavis.androidtask.data.repo.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers
import org.mockito.Mockito.*

@RunWith(JUnit4::class)
class UserRepositoryTest {

    private lateinit var repository: UserRepository

    private val userDao = mock(UserDao::class.java)
    private val userService = mock(UserService::class.java)
    private val remoteDataSource = RemoteDataSource(userService)
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup(){
        val database = mock(UserDataBase::class.java)
        `when`(database.userDao()).thenReturn(userDao)
        `when`(database.runInTransaction(ArgumentMatchers.any())).thenCallRealMethod()

        repository = UserRepository(remoteDataSource, userDao)
    }

    @Test
    fun getUserFromApi(){
        runBlocking {
            repository.observePagedUser(true, coroutineScope)
            verify(userDao, never()).getPagedUser()
            verifyNoInteractions(userDao)
        }
    }

}