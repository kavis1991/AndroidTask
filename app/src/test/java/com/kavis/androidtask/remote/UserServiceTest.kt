package com.kavis.androidtask.remote

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.kavis.androidtask.data.remote.UserService
import com.kavis.androidtask.util.TEST_USER_ID
import junit.framework.Assert.assertNotNull
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@RunWith(JUnit4::class)
class UserServiceTest {

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var service : UserService
    private lateinit var mockWebServer: MockWebServer


    @Before
    fun createService(){
        mockWebServer = MockWebServer()
        mockWebServer.start()
        service = Retrofit.Builder()
            .baseUrl(mockWebServer.url(""))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UserService::class.java)
    }


    @Test
    fun requestUserList(){
        runBlocking {
            enqueueResponse("users.json")
            val response = service.getUserList(0, 10).body()
            val request = mockWebServer.takeRequest()

            assertNotNull(response)
            assertThat(request.path, equals("/data/v1/user"))
            assertThat(response?.data?.size, `is`(10))
        }
    }

    @Test
    fun getUserResponse() {
        runBlocking {
            enqueueResponse("user.json")
            val response = service.getUser(TEST_USER_ID).body()
            val request = mockWebServer.takeRequest()

            assertThat(request.path, equals("/data/v1/user/${TEST_USER_ID}"))
            assertThat(response?.id, equals(TEST_USER_ID))
        }
    }

    @After
    fun stopServer() {
        mockWebServer.shutdown()
    }


    private fun enqueueResponse(fileName: String) {
        javaClass.classLoader?.getResourceAsStream("api-response/$fileName")?.let {
                inputStream ->
            val source = inputStream.source().buffer()
            val mockResponse = MockResponse()
            mockWebServer.enqueue(mockResponse.setBody(
                source.readString(Charsets.UTF_8))
            )
        }
    }
}