package com.kavis.androidtask.data.pagination

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PagedList
import com.kavis.androidtask.data.local.UserDao
import com.kavis.androidtask.data.models.User
import com.kavis.androidtask.data.remote.RemoteDataSource
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class UserPageDataSourceFactory @Inject constructor(
    private val dataSource: RemoteDataSource,
    private val dao: UserDao,
    private val scope: CoroutineScope
) : DataSource.Factory<Int, User>() {

    val liveData = MutableLiveData<UserPageDataSource>()

    override fun create(): DataSource<Int, User> {
        val source = UserPageDataSource(dataSource, dao, scope)
        liveData.postValue(source)
        return source
    }

    companion object {
        private const val LIMIT = 20
        fun pagedListConfig() = PagedList.Config.Builder()
            .setInitialLoadSizeHint(LIMIT)
            .setPageSize(LIMIT)
            .setEnablePlaceholders(true)
            .build()
    }
}