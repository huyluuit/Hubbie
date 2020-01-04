package com.example.hubbie.modules.message.presenter

import com.example.hubbie.entities.Message
import com.example.hubbie.modules.message.OnGetData
import com.example.hubbie.utilis.firestore.FirestoreMessageUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class MessagePresenter {

    fun onListentToNewMessage(senderID: String, receiverID: String, listener: OnGetData<Message>) {
        FirestoreMessageUtil.doMessageListener(senderID, receiverID)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribeBy(
                onNext = {
                    listener.onSuccess(it)
                },
                onError = {
                    listener.onError()
                }
            )
    }

    fun getHistoryMessage(
        senderID: String,
        receiverID: String,
        listener: OnGetData<ArrayList<Message>>
    ) {
        FirestoreMessageUtil.getBaseMessages(senderID, receiverID)
            .observeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    listener.onSuccess(it)
                },
                onError = {
                    listener.onError()
                }
            )
    }
}