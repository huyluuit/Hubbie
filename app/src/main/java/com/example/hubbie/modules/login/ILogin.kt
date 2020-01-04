package com.example.hubbie.modules.login

import com.example.hubbie.entities.User
import com.example.hubbie.entities.UserLogin
import com.example.hubbie.modules.base.BaseContract

interface ILogin {

    interface Callbacks {
        fun onLoginComplete(result: Pair<Boolean, String>)
        fun onRequestDataSuccess()
    }

    interface Presenter : BaseContract.BasePresenter {
        fun loginClicked(userLogin: UserLogin)
        fun registerClicked()
        fun forgotAccountClicked()
        fun validateUserInput(email: String, pwd: String): Boolean
    }

    interface Interactor : BaseContract.BaseInteractor {
        fun checkCurrentUser(email: String, pwd: String)
        fun setUserToPreferences(user: User)
        fun getUserFromFB(uid: String)
    }

    interface Route {
        fun navigateToMainFragment()
        fun navigateToRegister()
        fun navigateToForgotAccount()
    }
}