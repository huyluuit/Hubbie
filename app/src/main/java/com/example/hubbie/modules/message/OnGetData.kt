package com.example.hubbie.modules.message

interface OnGetData<T> {

    fun onSuccess(data: T?)

    fun onError()

}