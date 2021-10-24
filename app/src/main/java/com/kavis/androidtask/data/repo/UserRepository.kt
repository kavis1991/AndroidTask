package com.kavis.androidtask.data.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.liveData
import androidx.paging.LivePagedListBuilder
import com.kavis.androidtask.data.local.UserDao
import com.kavis.androidtask.data.models.Data
import com.kavis.androidtask.data.models.User
import com.kavis.androidtask.data.pagination.UserPageDataSourceFactory
import com.kavis.androidtask.data.remote.RemoteDataSource
import com.kavis.androidtask.util.NetworkResult
import com.kavis.androidtask.util.performGetOperation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject


class UserRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: UserDao
) {

    fun getUser(id: String): LiveData<NetworkResult<User>> = liveData(Dispatchers.IO) {
        emit(NetworkResult.loading())
        val responseStatus = remoteDataSource.getUser(id)
        if (responseStatus.status == NetworkResult.Status.SUCCESS) {
            emit(NetworkResult.success(responseStatus.data!!))

        } else if (responseStatus.status == NetworkResult.Status.ERROR) {
            emit(NetworkResult.error(responseStatus.message!!))
        }
    }

    fun observePagedUser(connectivityAvailable : Boolean, coroutineScope: CoroutineScope): Data<User> {
        return if (connectivityAvailable)
            observeRemotePagedUser(coroutineScope)
        else observeLocalPagedUser()
    }

    private fun observeLocalPagedUser(): Data<User> {

        val dataSourceFactory = localDataSource.getPagedUser()

        val liveData = MutableLiveData<NetworkResult.Status>()
        liveData.postValue(NetworkResult.Status.SUCCESS)

        return Data(LivePagedListBuilder(dataSourceFactory,
            UserPageDataSourceFactory.pagedListConfig()).build(), liveData)
    }

    private fun observeRemotePagedUser(ioCoroutineScope: CoroutineScope): Data<User> {
        val dataSourceFactory = UserPageDataSourceFactory(remoteDataSource,
            localDataSource, ioCoroutineScope)

        val networkState = Transformations.switchMap(dataSourceFactory.liveData) {
            it.networkState
        }
        return Data(LivePagedListBuilder(dataSourceFactory,
                UserPageDataSourceFactory.pagedListConfig()).build(), networkState)
    }
}
