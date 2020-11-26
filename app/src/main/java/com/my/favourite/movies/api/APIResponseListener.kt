package com.my.favourite.movies.api

interface APIResponseListener {
    fun onSuccess(response: Any?)
    fun onFail(message: String?)
}