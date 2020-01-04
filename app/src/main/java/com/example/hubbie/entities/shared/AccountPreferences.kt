package com.example.hubbie.entities.shared

import android.content.Context
import android.content.SharedPreferences
import com.example.hubbie.entities.User
import com.google.gson.Gson

class AccountPreferences(context: Context) : ISharedPreferences.AccountPreferences {


    companion object {
        private const val PREF_USER = "USER"
        private const val PRIVATE_MODE = 0
    }

    private var prefMap: MutableMap<String, *>
    private var pref: SharedPreferences = context.getSharedPreferences(PREF_USER, PRIVATE_MODE)
    private var gson = Gson()

    init {
        prefMap = pref.all
    }


    override fun getBaseAccount(): User {
        val userJson = pref.getString(PREF_USER, "")
        val user: User = gson.fromJson(userJson, User::class.java)
        return user
    }

    override fun getAccountExist(): Boolean {
        return pref.contains(PREF_USER)
    }

    override fun setBaseAccount(user: User) {
        val userJson = gson.toJson(user)
        pref.edit().putString(PREF_USER, userJson).apply()
    }

    override fun removeBaseAccount(): Boolean {
        return pref.edit().remove(PREF_USER).commit()
    }

}