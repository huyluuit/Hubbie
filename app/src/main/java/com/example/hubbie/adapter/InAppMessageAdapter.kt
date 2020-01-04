package com.example.hubbie.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hubbie.R
import com.example.hubbie.entities.Message
import kotlinx.android.synthetic.main.item_message.view.*

class InAppMessageAdapter : RecyclerView.Adapter<InAppMessageAdapter.ViewHolder> {

    private var listSMS = ArrayList<Message>()
    private var context: Context? = null
    private var userId: String? = null

    constructor(context: Context?, listSMS: ArrayList<Message>, userId: String?) {
        this.context = context
        this.listSMS = listSMS
        this.userId = userId
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): InAppMessageAdapter.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_message,
                parent,
                false
            )
        )
    }

    fun updateAdapter(listSMS: ArrayList<Message>) {
        this.listSMS = listSMS
        notifyDataSetChanged()
    }

    fun updateAdapterAtPosition(listSMS: ArrayList<Message>, position: Int) {
        this.listSMS = listSMS
        notifyItemRangeChanged(position, 1)
    }

    override fun getItemCount(): Int {
        return listSMS.size
    }

    override fun onBindViewHolder(holder: InAppMessageAdapter.ViewHolder, position: Int) {
        val sms = listSMS.get(position).message
        val uid = listSMS.get(position).uid
        if (userId.equals(uid)) {
            holder.itemView.layout_show_message.setBackgroundResource(R.drawable.bg_text_message_sender)
        } else {
            holder.itemView.layout_show_message.setBackgroundResource(R.drawable.bg_text_message_receiver)
        }

        if (sms != null) {
            holder.itemView.txt_message.text = sms
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}