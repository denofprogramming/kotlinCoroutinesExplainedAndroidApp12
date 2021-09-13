package com.denofprogramming.mycoroutineapplication

import android.app.Application
import timber.log.Timber

class MyCoroutineApp : Application() {


    init {
        System.setProperty(
            "kotlinx.coroutines.debug",
            if (BuildConfig.DEBUG) {
                "on"
            } else {
                "off"
            }
        )

        Timber.plant(Timber.DebugTree())
        Timber.i("My Coroutine App Application instantiated...")
    }

}