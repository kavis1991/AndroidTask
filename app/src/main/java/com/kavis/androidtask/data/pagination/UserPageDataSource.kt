package com.kavis.androidtask.data.pagination

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.kavis.androidtask.data.local.UserDao
import com.kavis.androidtask.data.models.User
import com.kavis.androidtask.data.remote.RemoteDataSource
import com.kavis.androidtask.util.NetworkResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class UserPageDataSource @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val userDao: UserDao,
    private val coroutineScope: CoroutineScope
) : PageKeyedDataSource<Int, User>() {

    val networkState = MutableLiveData<NetworkResult.Status>()

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, User>
    ) {
        networkState.postValue(NetworkResult.Status.LOADING)
        fetchData(0, params.requestedLoadSize) {
            callback.onResult(it, null, 1)
        }
    }
    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, User>) {
        networkState.postValue(NetworkResult.Status.LOADING)
        val page = params.key
        fetchData(page, params.requestedLoadSize) {
            callback.onResult(it, page + 1)
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, User>) {
        val page = params.key
        fetchData(page, params.requestedLoadSize) {
            callback.onResult(it, page - 1)
        }
    }

    private fun fetchData(page: Int, limit: Int, callback: (List<User>) -> Unit) {
        coroutineScope.launch {
            val response = remoteDataSource.getUsers(page, limit)

            if (response.status == NetworkResult.Status.SUCCESS) {
                val results = response.data!!.data
                userDao.insertAllUser(results)
                callback(results)
                networkState.postValue(NetworkResult.Status.SUCCESS)
            }else {
                networkState.postValue(NetworkResult.Status.ERROR)
            }
        }
    }

}