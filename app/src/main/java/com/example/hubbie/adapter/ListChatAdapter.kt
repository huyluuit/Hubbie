package com.example.hubbie.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hubbie.R
import com.example.hubbie.entities.User
import kotlinx.android.synthetic.main.item_list_chat.view.*

class ListChatAdapter : RecyclerView.Adapter<ListChatAdapter.ViewHolder> {

    private var context: Context? = null
    private var listChat: ArrayList<User>? = null
    private var listenner: OnGetUserChatList? = null

    constructor(context: Context?, listChat: ArrayList<User>?, listenner: OnGetUserChatList?) {
        this.context = context
        this.listChat = listChat
        this.listenner = listenner
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ListChatAdapter.ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_list_chat,
                parent,
                false
            )
        )
    }

    fun updateAdapter(listChat: ArrayList<User>) {
        this.listChat = listChat
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return listChat?.size ?: 0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = listChat?.get(position)
        if (user != null) {
            holder.itemView.txt_name.text = user.fullName
            holder.itemView.txt_role.text = user.role
        }
        holder.itemView.setOnClickListener {
            listenner?.onItemClick(position)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}

interface OnGetUserChatList {
    fun onItemClick(position: Int)
}