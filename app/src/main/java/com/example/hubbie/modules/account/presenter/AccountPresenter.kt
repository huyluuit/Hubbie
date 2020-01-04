package com.example.hubbie.modules.account.presenter

import android.content.Context
import com.example.hubbie.entities.User
import com.example.hubbie.entities.shared.AccountPreferences
import com.example.hubbie.modules.account.IAccount
import com.example.hubbie.modules.account.route.AccountRoute

class AccountPresenter(private val context: Context?) : IAccount.Presenter {

    private val router = AccountRoute()

    override fun pwdChangeClicked(newPwd: String) {
    }

    override fun phoneChangeClicked(newPhone: String) {
    }

    override fun nameDisplayChangeClicked(newNameDisplay: String) {
    }

    override fun getUser(): User? {
        if (context != null) {
            return AccountPreferences(context).getBaseAccount()
        }
        return null
    }

}