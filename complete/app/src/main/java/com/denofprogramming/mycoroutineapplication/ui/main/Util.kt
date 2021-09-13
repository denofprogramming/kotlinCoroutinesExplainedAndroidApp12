package com.denofprogramming.mycoroutineapplication.ui.main

import android.content.res.Resources
import android.graphics.Bitmap
import com.denofprogramming.mycoroutineapplication.R
import com.denofprogramming.mycoroutineapplication.shared.Resource

fun convertResourceToString(item: Resource<Bitmap>?, resources: Resources): String {
    if (item == null) {
        return resources.getString(R.string.noneLoaded)
    }

    return when {
        item.isNone() ->"No Resource Available"
        item.isLoading() -> resources.getString(R.string.loadingImage)
        item.isSuccess() -> resources.getString(R.string.loaded)
        item.isError() -> resources.getString(R.string.loadingImageError, item.message)
        else -> resources.getString(R.string.noneLoaded)
    }
}


fun convertResourceToBoolean(result: Resource<Bitmap>?): Boolean {
    return true
}
