package com.example.hubbie.utilis.authentication

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import io.reactivex.Single

object FBAuth {

    private val auth = FirebaseAuth.getInstance()

    fun loginEmailPwd(email: String, pwd: String): Single<Pair<Boolean, String>> {
        return Single.create {
            auth.signInWithEmailAndPassword(email, pwd)
                .addOnCompleteListener { task: Task<AuthResult> ->
                    if (task.isSuccessful) {
                        if (auth.currentUser != null) {
                            it.onSuccess(Pair(true, auth.currentUser?.uid.toString()))
                        } else {
                            it.onSuccess(Pair(false, " "))
                        }
                    } else {
                        it.onError(Throwable(task.isSuccessful.toString()))
                    }
                }
        }
    }

    fun createAccountByEmailandPwd(email: String, pwd: String): Single<Pair<Boolean, String>> {
        return Single.create {
            auth.createUserWithEmailAndPassword(email, pwd)
                .addOnCompleteListener { task: Task<AuthResult> ->
                    if (task.isSuccessful && auth.currentUser != null) {
                        it.onSuccess(Pair(task.isSuccessful, auth.currentUser!!.uid))
                    } else {
                        it.onSuccess(Pair(false, ""))
                    }
                }
        }
    }


}
