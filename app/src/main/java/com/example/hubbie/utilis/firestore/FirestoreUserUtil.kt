package com.example.hubbie.utilis.firestore

import android.util.Log
import com.example.hubbie.entities.User
import com.example.hubbie.utilis.ConvertDataUtils
import com.google.firebase.firestore.FirebaseFirestore
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

object FirestoreUserUtil {

    private const val USER_DISABLE =
        com.example.hubbie.BuildConfig.APPLICATION_ID + ".USER_HAS_DISABLE"
    const val COLLECTION_USER = "User"
    private val db = FirebaseFirestore.getInstance().collection(COLLECTION_USER)

    /* User DB Structure
     User(C) > User_Id(D) > RoomControl(C) > Room_Id(D) > RoomUser_Object: roomId: String?
                                                                           activate: Boolean?
     */

    fun getUser(userId: String): Single<User> {
        return Single.create {
            db.document(userId).get().addOnCompleteListener { task ->
                if (task.isSuccessful && task.result != null) {
                    Log.e("User", "NameDisplay: ${task.result?.data?.toString()}")
                    val user = ConvertDataUtils.convertDataToUser(task.result!!.data)
                    it.onSuccess(user)
                }
            }
        }
    }

    fun getBaseUserList(): Single<ArrayList<Any>> {
        return Single.create {
            // update later
        }
    }

    fun setUser(user: User?): Completable {
        return Completable.create {
            if (user != null) {
                db.document(user.uid!!).set(user).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        it.onComplete()
                    } else {
                        return@addOnCompleteListener
                    }
                }
            }
        }
    }

    fun doUserChangeListener(userId: String): Observable<User> {
        return Observable.create {
            db.document(userId)
                .addSnapshotListener { documentSnapshot, firebaseFirestoreException ->

                    if (firebaseFirestoreException != null) {
                        return@addSnapshotListener
                    }

                    if (documentSnapshot != null) {
                        val user = ConvertDataUtils.convertDataToUser(documentSnapshot.data)
                        it.onNext(user)
                    }
                }
        }
    }

    fun checkAdminId(userId: String): Single<Boolean> {
        return Single.create {
            db.document().get().addOnCompleteListener { task ->
                if (task.isSuccessful && task.result != null) {
//                    for(item in task.result.){
//
//                    }
                }
            }
        }
    }

}