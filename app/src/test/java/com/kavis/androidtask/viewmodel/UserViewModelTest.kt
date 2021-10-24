package com.kavis.androidtask.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.kavis.androidtask.data.repo.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.*

@RunWith(JUnit4::class)
class UserViewModelTest {

    private val repository: UserRepository = mock(UserRepository::class.java)
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun testNull(){
        verify(repository, never()).observePagedUser(false, coroutineScope)
        verify(repository, never()).observePagedUser(true, coroutineScope)
    }
}