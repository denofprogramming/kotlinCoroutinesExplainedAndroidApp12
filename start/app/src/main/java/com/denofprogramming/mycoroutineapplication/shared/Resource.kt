package com.denofprogramming.mycoroutineapplication.shared

data class Resource<out T>(
    val status: Status,
    val data: T? = null,
    val message: String = ""
) {

    fun isLoading(): Boolean {
        return when (status) {
            Status.NONE -> false
            Status.LOADING -> true
            Status.SUCCESS -> false
            Status.ERROR -> false
        }
    }

    fun isSuccess(): Boolean {
        return when (status) {
            Status.NONE -> false
            Status.LOADING -> false
            Status.SUCCESS -> true
            Status.ERROR -> false
        }
    }

    fun isError(): Boolean {
        return when (status) {
            Status.NONE -> false
            Status.LOADING -> false
            Status.SUCCESS -> false
            Status.ERROR -> true
        }
    }

    fun isNone(): Boolean {
        return when (status) {
            Status.NONE -> true
            Status.LOADING -> false
            Status.SUCCESS -> false
            Status.ERROR -> false
        }
    }

    companion object {

        fun <T> none(): Resource<T> {
            return Resource(Status.NONE)
        }

        fun <T> success(data: T?): Resource<T> {
            return Resource(Status.SUCCESS, data)
        }

        fun <T> error(msg: String): Resource<T> {
            return Resource(Status.ERROR, message = msg)
        }

        fun <T> error(data: T): Resource<T> {
            return Resource(Status.ERROR, data = data)
        }

        fun <T> loading(): Resource<T> {
            return Resource(Status.LOADING)
        }
    }
}