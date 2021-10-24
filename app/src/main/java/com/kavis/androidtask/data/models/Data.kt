package com.kavis.androidtask.data.models

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.kavis.androidtask.util.NetworkResult

data class Data<T>(
    val pagedList: LiveData<PagedList<T>>,
    val networkState: LiveData<NetworkResult.Status>)