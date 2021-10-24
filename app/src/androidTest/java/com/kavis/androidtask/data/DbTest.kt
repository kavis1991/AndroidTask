
import android.util.Log
import androidx.arch.core.executor.testing.CountingTaskExecutorRule
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room

import org.junit.Before

import androidx.test.core.app.ApplicationProvider

import org.junit.Rule

import androidx.test.runner.AndroidJUnit4
import com.kavis.androidtask.data.local.UserDao
import com.kavis.androidtask.data.local.UserDataBase
import org.junit.After

import org.junit.runner.RunWith
import com.kavis.androidtask.util.getValue
import com.kavis.androidtask.util.testUser1
import com.kavis.androidtask.util.testUser2
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.equalTo
import org.junit.Assert.assertThat
import org.junit.Test
import java.util.concurrent.TimeUnit


@RunWith(AndroidJUnit4::class)
class DbTest {

    @Rule
    @JvmField
    val countingTaskExecutorRule = CountingTaskExecutorRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var dataBase: UserDataBase
    private lateinit var userDao: UserDao

    @Before
    fun initDb() {
        dataBase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            UserDataBase::class.java
        ).allowMainThreadQueries().build()

        userDao = dataBase.userDao()

        runBlocking {
            userDao.insertAllUser(listOf(testUser1.copy(), testUser2.copy()))
        }
    }

    @After
    fun closeDb() {
        countingTaskExecutorRule.drainTasks(10, TimeUnit.SECONDS)
        dataBase.close()
    }

    @Test
    @Throws(InterruptedException::class)
    fun testGetUser() {
        val list = getValue(userDao.getUsers())
        assertThat(list.size, equalTo(2))
        assertThat(list[0], equalTo(testUser1))
        assertThat(list[1], equalTo(testUser2))
    }

}