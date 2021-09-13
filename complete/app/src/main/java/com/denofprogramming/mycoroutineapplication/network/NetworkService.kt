package com.denofprogramming.mycoroutineapplication.network

import android.graphics.Bitmap

interface NetworkService {


    fun cancel()

    suspend fun getImage(id: String): Bitmap?

}