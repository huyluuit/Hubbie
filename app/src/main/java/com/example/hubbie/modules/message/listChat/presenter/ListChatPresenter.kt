package com.example.hubbie.modules.message.listChat.presenter

import android.content.Context
import android.util.Log
import com.example.hubbie.entities.User
import com.example.hubbie.modules.message.OnGetData
import com.example.hubbie.modules.message.listChat.IListChat
import com.example.hubbie.modules.message.listChat.router.ListChatRouter
import com.example.hubbie.utilis.firestore.FirestoreMessageUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class ListChatPresenter : IListChat.Presenter {

    private val router = ListChatRouter()

    override fun navigationMessage(context: Context?, uid: String, receiverId: String) {
        router.navigationMessage(context, uid, receiverId)
    }

    override fun getListChat(user: User, listener: OnGetData<ArrayList<User>>) {
        Log.d("huanhuan", "Get 1")
        FirestoreMessageUtil.getUserChatList(user)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribeBy(
                onSuccess = {
                    listener.onSuccess(it)
                },
                onError = {
                    Log.d("huanhuan", "Get 2")
                    listener.onError()
                }
            )
    }
}