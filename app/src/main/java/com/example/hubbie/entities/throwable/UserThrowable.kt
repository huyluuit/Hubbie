package com.example.hubbie.entities.throwable

import com.example.hubbie.BuildConfig

class UserThrowable(private val error: String) : Throwable() {

    companion object {
        private const val USER_NULL = BuildConfig.APPLICATION_ID + ".USER_IS_NULL"
        private const val USER_DISABLE = BuildConfig.APPLICATION_ID + ".USER_HAS_DISABLE"
    }

    override fun getLocalizedMessage(): String? {
        return error
    }
}