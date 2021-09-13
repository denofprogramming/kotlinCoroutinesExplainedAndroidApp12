package com.denofprogramming.mycoroutineapplication.network

import android.graphics.Bitmap
import com.denofprogramming.mycoroutineapplication.shared.uilt.logMessage
import kotlinx.coroutines.*

class MockNetworkService : NetworkService {


    private var _job = Job()

    override fun cancel() {
        _job.cancel()
        _job = Job()
    }


    //Network library supporting Coroutines...
    override suspend fun getImage(id: String): Bitmap? =
        try {
            withContext(Dispatchers.IO + _job) {
                logMessage("MockNetworkService.getImage() downloading...")
                delay(5000) // Simulate network call and download...
                return@withContext allImages[id.toInt()]
            }
        } catch (ce: CancellationException) {
            logMessage("getImageCancelled")
            throw ce
        }


    companion object {

        fun build(): MockNetworkService {
            return MockNetworkService()
        }

    }
}