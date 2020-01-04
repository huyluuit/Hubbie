package com.example.hubbie.modules.login.interactor

import android.content.Context
import android.util.Log
import com.example.hubbie.R
import com.example.hubbie.entities.User
import com.example.hubbie.entities.shared.AccountPreferences
import com.example.hubbie.modules.login.ILogin
import com.example.hubbie.utilis.GeneralUtils
import com.example.hubbie.utilis.authentication.FBAuth
import com.example.hubbie.utilis.firestore.FirestoreUserUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class LoginInteractor(private val context: Context, private val callbacks: ILogin.Callbacks) :
    ILogin.Interactor {

    private val auth = FBAuth
    private val compositeDisposable = CompositeDisposable()

    override fun checkCurrentUser(email: String, pwd: String) {
        val checkAccountDisposable = auth.loginEmailPwd(email, pwd)
            .subscribeOn(Schedulers.io())
            .timeout(10, TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread()).subscribeBy(
                onSuccess = {
                    callbacks.onLoginComplete(it)
                    Log.d("checkUser", "UID: ${it.second}")
                },
                onError = {
                    GeneralUtils.showingToast(
                        context.applicationContext,
                        context.getString(R.string.timeout_request)
                    )
                    callbacks.onLoginComplete(Pair(false, ""))
                }
            )

        compositeDisposable.add(checkAccountDisposable)
    }

    override fun getUserFromFB(uid: String) {
        val userDisposable = FirestoreUserUtil.getUser(uid)
            .subscribeOn(Schedulers.io())
            .timeout(10, TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread()).subscribeBy(
                onSuccess = {
                    AccountPreferences(context).setBaseAccount(it)
                    callbacks.onRequestDataSuccess()
                },
                onError = {
                    GeneralUtils.showingToast(
                        context.applicationContext,
                        context.getString(R.string.timeout_request)
                    )
                    callbacks.onLoginComplete(Pair(false, ""))
                }
            )
        compositeDisposable.addAll(userDisposable)
    }

    override fun setUserToPreferences(user: User) {
        AccountPreferences(context).setBaseAccount(user)
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
    }


}