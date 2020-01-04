package com.example.hubbie.modules.message.listChat.router

import android.content.Context
import com.example.hubbie.MainActivity
import com.example.hubbie.modules.message.listChat.IListChat
import com.example.hubbie.modules.message.view.MessageFragment

class ListChatRouter : IListChat.Router {

    override fun navigationMessage(context: Context?, uid: String, receiverId: String) {
        (context as MainActivity).changeFragment(MessageFragment.newInstance(uid, receiverId))
    }
}