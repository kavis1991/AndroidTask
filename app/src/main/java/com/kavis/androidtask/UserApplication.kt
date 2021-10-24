package com.kavis.androidtask

import android.app.Application
import com.kavis.androidtask.util.ConnectivityUtil
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class UserApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        ConnectivityUtil.monitorNetwork(this)
    }

    override fun onTerminate() {
        super.onTerminate()
        ConnectivityUtil.stopNetworkCallback(this)
    }
}