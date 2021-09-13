package com.denofprogramming.mycoroutineapplication.repository.time

import android.icu.text.SimpleDateFormat
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.denofprogramming.mycoroutineapplication.shared.Resource
import com.denofprogramming.mycoroutineapplication.shared.uilt.logMessage
import java.util.*
import kotlin.concurrent.thread


class DefaultClock {

    private val _time = MutableLiveData<Resource<Long>>().apply { value = Resource.none() }

    val time: LiveData<Resource<Long>>
        get() = _time


    fun start() {
       thread(start = true) {
            while (true) {
                tikTok()
            }
        }
    }

    private fun tikTok() {
        System.currentTimeMillis().run {
            notifyTime(Resource.success(this))
        }
        Thread.sleep(1000)
    }


    private fun notifyTime(resource: Resource<Long>) {

        // This will fail. Assumes running on Main
       // _time.value = resource

        //Uses a Handler to Post onto the Main Thread.
       Handler(Looper.getMainLooper()).post {
            //logMessage("Clock posting result in Looper Handler callback()")
            _time.value = resource
        }

        //Live Data provides us a convenience method postValue.
        //_time.postValue(resource)

    }

    fun timeStampToTime(timestamp: Resource<Long>): String {
        Thread.sleep(10)  // Simulate long operation
        timestamp.data?.let {
            val date = Date(it)
            return SimpleDateFormat("HH:mm:ss").format(date)
        }
        return timestamp.message
    }


    companion object {
        fun build(): DefaultClock {
            return DefaultClock()
        }
    }

}