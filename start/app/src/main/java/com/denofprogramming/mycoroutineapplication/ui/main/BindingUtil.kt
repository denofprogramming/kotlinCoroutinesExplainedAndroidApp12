package com.denofprogramming.mycoroutineapplication.ui.main

import android.graphics.Bitmap
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.denofprogramming.mycoroutineapplication.R
import com.denofprogramming.mycoroutineapplication.shared.Resource
import com.denofprogramming.mycoroutineapplication.shared.uilt.logMessage


@BindingAdapter("android:liveDataImageBitmap")
fun ImageView.setLiveDataImageBitmap(item: Resource<Bitmap>?) {
    logMessage("liveDataImageBitmap -> State: $item")
    item?.let {
        when {
            item.isNone() -> setImageResource(R.drawable.ic_broken_image)
            item.isLoading() -> setImageResource(R.drawable.loading_animation)
            item.isSuccess() -> setImageBitmap(item.data)
            item.isError() -> setImageResource(R.drawable.ic_broken_image)
            else -> setImageResource(R.drawable.ic_broken_image)
        }
    }
}

@BindingAdapter("android:imageLoadStatus")
fun TextView.setImageLoadStatus(item: Resource<Bitmap>?) {
    text = convertResourceToString(item, context.resources)
}


@BindingAdapter("android:loadingEnable")
fun Button.setLoadingEnable(item: Resource<Bitmap>?) {
    isEnabled = convertResourceToBoolean(item)
}

@BindingAdapter("android:cancellingEnable")
fun Button.setCancellingEnable(item: Resource<Bitmap>?) {
    //isEnabled = !convertResourceToBoolean(item)
    isEnabled = true
}
