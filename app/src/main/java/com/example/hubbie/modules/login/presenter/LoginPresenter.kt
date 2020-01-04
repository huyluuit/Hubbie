package com.example.hubbie.modules.login.presenter

import com.example.hubbie.R
import com.example.hubbie.entities.UserLogin
import com.example.hubbie.modules.login.ILogin
import com.example.hubbie.modules.login.interactor.LoginInteractor
import com.example.hubbie.modules.login.route.LoginRoute
import com.example.hubbie.modules.login.view.LoginFragment
import com.example.hubbie.utilis.GeneralUtils

class LoginPresenter(private val fragment: LoginFragment) : ILogin.Presenter, ILogin.Callbacks {

    private lateinit var email: String
    private lateinit var pwd: String
    private var interactor: LoginInteractor? = LoginInteractor(fragment.context!!, this)
    private var route: LoginRoute? = LoginRoute(fragment)

    override fun onLoginComplete(result: Pair<Boolean, String>) {
        if (result.first) {
            GeneralUtils.showingToast(
                fragment.context?.applicationContext,
                fragment.context?.getString(R.string.login_success)
            )
            interactor?.getUserFromFB(result.second)
        } else {
            fragment.dismissLoading()
            GeneralUtils.showingToast(
                fragment.context?.applicationContext,
                fragment.context?.getString(R.string.login_failed)
            )
        }
    }

    override fun onRequestDataSuccess() {
        fragment.dismissLoading()
        route?.navigateToMainFragment()
    }

    override fun loginClicked(userLogin: UserLogin) {

        if (validateUserInput(userLogin.userName, userLogin.pwd)) {
            // Call login in firebase function if success get uid and write to preferences
            fragment.showLoading()
            interactor?.checkCurrentUser(userLogin.userName, userLogin.pwd)
        } else {
            // Notify to user via Toast or Dialog
            val context = fragment.context?.applicationContext
            GeneralUtils.showingToast(context, context?.getString(R.string.email_pwd_empty))
        }
    }

    override fun validateUserInput(email: String, pwd: String): Boolean {

        val context = fragment.context
        if (email.isEmpty()) {
            GeneralUtils.showingToast(
                context?.applicationContext,
                context?.getString(R.string.email_empty)
            )
            return false
        }

        if (pwd.isEmpty()) {
            GeneralUtils.showingToast(
                context?.applicationContext,
                context?.getString(R.string.pwd_empty)
            )
            return false
        }

        return true
    }

    override fun registerClicked() {
        route?.navigateToRegister()
    }

    override fun forgotAccountClicked() {
        route?.navigateToForgotAccount()
    }

    override fun onDestroy() {
        route = null
        interactor = null
    }

}