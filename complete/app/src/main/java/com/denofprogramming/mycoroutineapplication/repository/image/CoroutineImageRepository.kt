package com.denofprogramming.mycoroutineapplication.repository.image

import android.graphics.Bitmap
import com.denofprogramming.mycoroutineapplication.network.NetworkService
import com.denofprogramming.mycoroutineapplication.network.allImages
import com.denofprogramming.mycoroutineapplication.shared.Resource
import com.denofprogramming.mycoroutineapplication.shared.uilt.logMessage

class CoroutineImageRepository(
    private val _networkService: NetworkService
) {

    private var _count: Int = -1


    fun cancel() {
        _networkService.cancel()
    }


    suspend fun fetchImage(imageId: String): Resource<Bitmap> {
        logMessage("Start fetchImage() downloading...")
        val image = _networkService.getImage(imageId)
        return Resource.success(image)
    }


    fun nextImageId(): String {
        _count++
        if (_count > allImages.size - 1) {
            _count = 0
        }
        return _count.toString()
    }

    companion object {

        fun build(networkService: NetworkService): CoroutineImageRepository {
            return CoroutineImageRepository(networkService)
        }
    }


}