package com.example.hubbie.modules.message.listChat

import android.content.Context
import com.example.hubbie.entities.User
import com.example.hubbie.modules.message.OnGetData

interface IListChat {

    interface View

    interface Presenter {
        fun navigationMessage(context: Context?, uid: String, receiverId: String)
        fun getListChat(user: User, listener: OnGetData<ArrayList<User>>)
    }

    interface Router {
        fun navigationMessage(context: Context?, uid: String, receiverId: String)
    }
}