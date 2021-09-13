package com.denofprogramming.mycoroutineapplication.shared.uilt

import timber.log.Timber

fun logMessage(msg: String){
    Timber.i ("Running on: [${Thread.currentThread().name}] | $msg")
}
