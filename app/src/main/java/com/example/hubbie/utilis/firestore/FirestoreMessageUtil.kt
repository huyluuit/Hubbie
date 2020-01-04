package com.example.hubbie.utilis.firestore

import com.example.hubbie.entities.Message
import com.example.hubbie.entities.User
import com.example.hubbie.utilis.ConvertDataUtils
import com.example.hubbie.utilis.firestore.FirestoreUserUtil.COLLECTION_USER
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import io.reactivex.Observable
import io.reactivex.Single

object FirestoreMessageUtil {

    const val COLLECTION_MESSAGE_CHANNEL = "MessageChannel"
    const val DOCUMENT_DETAIL = "Detail"
    const val DOCUMENT_DETAIL_CONTENT = "DetailContent"
    private val db = FirebaseFirestore.getInstance().collection(COLLECTION_MESSAGE_CHANNEL)

    fun setMessage(userId: String, receiverID: String, message: Message) {
        db.document(userId).collection(DOCUMENT_DETAIL).document(receiverID).collection(
            DOCUMENT_DETAIL_CONTENT
        ).document(message.time ?: "").set(message)
    }

    fun getBaseMessages(userId: String, destUid: String): Single<ArrayList<Message>> {

        return Single.create {
            db.document(userId).collection(DOCUMENT_DETAIL).document(destUid).collection(
                DOCUMENT_DETAIL_CONTENT
            ).get().addOnCompleteListener { task ->
                if (task.isSuccessful && task.result != null) {
                    val result = ArrayList<Message>()
                    for (item in task.result!!.documents) {
                        result.add(ConvertDataUtils.convertDataToMessage(item.data))
                    }
                    it.onSuccess(result)
                } else {
                    return@addOnCompleteListener
                }
            }
        }

    }

    fun doMessageListener(userId: String, destUid: String): Observable<Message> {
        return Observable.create {
            db.document(userId).collection(DOCUMENT_DETAIL).document(destUid).collection(
                DOCUMENT_DETAIL_CONTENT
            ).addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                if (firebaseFirestoreException != null) {
                    return@addSnapshotListener
                }

                if (querySnapshot != null) {
                    for (item in querySnapshot.documentChanges) {
                        if (item.type == DocumentChange.Type.ADDED) {
                            it.onNext(ConvertDataUtils.convertDataToMessage(item.document.data))
                        }
                    }
                }
            }
        }
    }

    fun getUserChatList(user: User): Single<ArrayList<User>> {
        return Single.create {
            val userDb = FirebaseFirestore.getInstance().collection(COLLECTION_USER).get()
            userDb.addOnCompleteListener { task ->
                if (task.isSuccessful && task.result != null) {
                    val result = ArrayList<User>()
                    for (item in task.result!!.documents) {
                        val userData = ConvertDataUtils.convertDataToUser(item.data)
                        when (user.role) {
                            "admin" -> {
                                if (userData.adminId == user.uid) {
                                    result.add(userData)
                                }
                            }
                            "roomAdmin" -> {
                                if (userData.uid == user.adminId) {
                                    result.add(userData)
                                }

                                if (userData.adminId == user.uid) {
                                    result.add(userData)
                                }
                            }
                            "client" -> {
                                if (userData.uid == user.adminId) {
                                    result.add(userData)
                                }
                            }
                        }
                    }
                    it.onSuccess(result)
                }
            }
        }
    }

}