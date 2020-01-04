package com.example.hubbie.modules.register.interactor

import android.content.Context
import com.example.hubbie.R
import com.example.hubbie.entities.User
import com.example.hubbie.entities.shared.AccountPreferences
import com.example.hubbie.modules.register.IRegister
import com.example.hubbie.utilis.GeneralUtils
import com.example.hubbie.utilis.authentication.FBAuth
import com.example.hubbie.utilis.firestore.FirestoreUserUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class RegisterInteractor(
    private val context: Context?,
    private val callbacks: IRegister.Callbacks
) : IRegister.Interactor {

    private val compositeDisposable = CompositeDisposable()

    override fun setUser(user: User) {
        val userDisposable = FBAuth.createAccountByEmailandPwd(user.email!!, user.pwd!!)
            .timeout(10, TimeUnit.SECONDS).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribeBy(
                onSuccess = {
                    if (it.first) {
                        user.uid = it.second
                        val userDisposable1 = FirestoreUserUtil.setUser(user)
                            .observeOn(AndroidSchedulers.mainThread()).subscribeBy(

                                onComplete = {
                                    callbacks.onRegisterSuccess()
                                    if (context != null) {
                                        AccountPreferences(context).setBaseAccount(user)
                                    }
                                },
                                onError = {
                                }

                            )
                        compositeDisposable.add(userDisposable1)
                    }
                },
                onError = {
                    GeneralUtils.showingToast(
                        context?.applicationContext,
                        context?.getString(R.string.timeout_request)
                    )
                }
            )
        compositeDisposable.add(userDisposable)
    }

    override fun validateAdminId(uid: String) {
        val adminDisposable = FirestoreUserUtil.checkAdminId(uid)
            .timeout(10, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribeBy(
                onSuccess = {
                    callbacks.onValidateAdminId(it)
                },
                onError = {
                    GeneralUtils.showingToast(
                        context?.applicationContext,
                        context?.getString(R.string.timeout_request)
                    )
                }
            )
        compositeDisposable.add(adminDisposable)
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
    }

}