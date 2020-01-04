package com.example.hubbie.modules.register.presenter

import com.example.hubbie.R
import com.example.hubbie.entities.User
import com.example.hubbie.modules.register.IRegister
import com.example.hubbie.modules.register.interactor.RegisterInteractor
import com.example.hubbie.modules.register.route.RegisterRoute
import com.example.hubbie.modules.register.view.RegisterFragment
import com.example.hubbie.utilis.GeneralUtils

class RegisterPresenter(private val fragment: RegisterFragment) : IRegister.Presenter,
    IRegister.Callbacks {

    private var interactor: RegisterInteractor? = RegisterInteractor(fragment.context, this)
    private var router: RegisterRoute? = RegisterRoute(fragment)
    private var user = User()

    override fun validateUser(
        email: String,
        phone: String,
        fullName: String,
        pwd: String,
        confirmPwd: String
    ): Boolean {

        val context = fragment.context
        if (email.isEmpty()) {
            GeneralUtils.showingToast(
                context?.applicationContext,
                context?.getString(R.string.email_empty)
            )
            return false
        } else if (phone.isEmpty()) {
            GeneralUtils.showingToast(
                context?.applicationContext,
                context?.getString(R.string.phone_empty)
            )
            return false
        } else if (fullName.isEmpty()) {
            GeneralUtils.showingToast(
                context?.applicationContext,
                context?.getString(R.string.fullname_empty)
            )
            return false

        } else if (pwd.isEmpty()) {
            GeneralUtils.showingToast(
                context?.applicationContext,
                context?.getString(R.string.pwd_empty)
            )
            return false

        } else if (confirmPwd.isEmpty()) {
            GeneralUtils.showingToast(
                context?.applicationContext,
                context?.getString(R.string.confirmPwd_empty)
            )
            return false
        }

        if (pwd != confirmPwd) {

            GeneralUtils.showingToast(
                context?.applicationContext,
                context?.getString(R.string.pwd_confirm_mismatch)
            )
            return false
        }


        return true
    }

    override fun onBackClicked() {
        router?.navigateToLogin()
    }

    override fun signInUser(
        email: String,
        phone: String,
        fullName: String,
        pwd: String,
        confirmPwd: String,
        adminId: String
    ) {

        if (validateUser(email, phone, fullName, pwd, confirmPwd)) {
            user =
                User(true, GeneralUtils.getTimeId(), email, phone, fullName, pwd, "client", adminId)
            fragment.showLoading()
            if (adminId.isNotEmpty()) {
                interactor?.validateAdminId(adminId)
            } else {
                user.role = "admin"
                interactor?.setUser(user)
            }
        }
    }

    override fun onRegisterSuccess() {
        fragment.dismissLoading()
        router?.navigateToRoom()
    }

    override fun onValidateAdminId(boolean: Boolean) {
        if (boolean) {
            interactor?.setUser(user)
        } else {
            fragment.dismissLoading()
            GeneralUtils.showingToast(
                fragment.context?.applicationContext,
                fragment.context?.getString(R.string.adminId_mismatch)
            )
        }
    }

    override fun onDestroy() {
        interactor = null
        router = null
    }

}