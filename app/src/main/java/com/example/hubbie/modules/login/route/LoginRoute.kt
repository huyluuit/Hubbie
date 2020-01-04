package com.example.hubbie.modules.login.route

import com.example.hubbie.BuildConfig
import com.example.hubbie.modules.forgotaccount.view.ForgotAccountFragment
import com.example.hubbie.modules.login.ILogin
import com.example.hubbie.modules.login.view.LoginFragment
import com.example.hubbie.modules.main.view.MainFragment
import com.example.hubbie.modules.register.view.RegisterFragment

class LoginRoute(private val fragment: LoginFragment) : ILogin.Route {

    companion object {
        private const val LOGIN = BuildConfig.APPLICATION_ID + ".backStacks.LOGIN"
    }

    override fun navigateToMainFragment() {
        fragment.fragmentManager!!.beginTransaction().apply {
            replace(fragment.id, MainFragment())
            disallowAddToBackStack()
        }.commit()
    }

    override fun navigateToRegister() {
        fragment.fragmentManager!!.beginTransaction().apply {
            replace(fragment.id, RegisterFragment())
            addToBackStack(LOGIN)
        }.commit()
    }

    override fun navigateToForgotAccount() {
        fragment.fragmentManager!!.beginTransaction().apply {
            replace(fragment.id, ForgotAccountFragment())
            addToBackStack(LOGIN)
        }.commit()
    }

}