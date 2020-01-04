package com.example.hubbie.entities.shared

import com.example.hubbie.entities.User

interface ISharedPreferences {

    interface AccountPreferences {
        fun setBaseAccount(user: User)
        fun getBaseAccount(): User
        fun getAccountExist(): Boolean
        fun removeBaseAccount(): Boolean
    }
}