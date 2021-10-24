package com.kavis.androidtask.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import kotlinx.coroutines.Dispatchers

fun <T, A> performGetOperation(databaseQuery: () -> LiveData<T>,
                               networkCall: suspend () -> NetworkResult<A>,
                               saveCallResult: suspend (A) -> Unit): LiveData<NetworkResult<T>> =
    liveData(Dispatchers.IO) {
        emit(NetworkResult.loading())
        val source = databaseQuery.invoke().map { NetworkResult.success<T>(it) }
        emitSource(source)

        val responseStatus = networkCall.invoke()

        if (responseStatus.status == NetworkResult.Status.SUCCESS) {
            saveCallResult(responseStatus.data!!)

        } else if (responseStatus.status == NetworkResult.Status.ERROR) {
            emit(NetworkResult.error(responseStatus.message!!))
            emitSource(source)
        }
    }