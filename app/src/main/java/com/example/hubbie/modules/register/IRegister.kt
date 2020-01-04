package com.example.hubbie.modules.register

import com.example.hubbie.entities.User
import com.example.hubbie.modules.base.BaseContract

interface IRegister {

    interface Callbacks {
        fun onValidateAdminId(boolean: Boolean)
        fun onRegisterSuccess()
    }

    interface Presenter : BaseContract.BasePresenter {

        fun onBackClicked()

        fun validateUser(
            email: String,
            phone: String,
            fullName: String,
            pwd: String,
            confirmPwd: String
        ): Boolean

        fun signInUser(
            email: String,
            phone: String,
            fullName: String,
            pwd: String,
            confirmPwd: String,
            adminId: String
        )
    }

    interface Interactor : BaseContract.BaseInteractor {
        fun setUser(user: User)
        fun validateAdminId(uid: String)
    }

    interface Route : BaseContract.BaseRouter {
        fun navigateToRoom()
        fun navigateToLogin()
    }

    interface View
}