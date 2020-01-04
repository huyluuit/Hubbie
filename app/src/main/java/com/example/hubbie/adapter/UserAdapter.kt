package com.example.hubbie.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hubbie.R
import com.example.hubbie.entities.User

class UserAdapter(private val data: ArrayList<User>, private val listener: Callbacks) :
    RecyclerView.Adapter<UserAdapter.DataViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        return DataViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_user,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.tvUserNameDisplay.text = data[position].fullName
        holder.tvUserState.text = data[position].adminId

        holder.llLogs.setOnClickListener {
            holder.bindOnLogClick(position, listener)
        }

        holder.llRole.setOnClickListener {
            holder.bindOnRoleClick(position, listener)
        }

        holder.llUserInfo.setOnClickListener {
            holder.bindOnUserInfoClick(position, listener)
        }
    }

    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvUserNameDisplay: TextView
        var tvUserState: TextView
        var llLogs: LinearLayout
        var llRole: LinearLayout
        var llUserInfo: LinearLayout

        init {
            tvUserNameDisplay = itemView.findViewById(R.id.tvUserNameDisplay)
            tvUserState = itemView.findViewById(R.id.tvUserState)
            llLogs = itemView.findViewById(R.id.llLogs)
            llRole = itemView.findViewById(R.id.llRole)
            llUserInfo = itemView.findViewById(R.id.llUserInfo)
        }

        fun bindOnLogClick(position: Int, listener: Callbacks) {
            listener.onLogClick(position)
        }

        fun bindOnRoleClick(position: Int, listener: Callbacks) {
            listener.onLogClick(position)
        }

        fun bindOnUserInfoClick(position: Int, listener: Callbacks) {
            listener.onUserInfoClick(position)
        }
    }

    interface Callbacks {
        fun onLogClick(position: Int)
        fun onRoleClick(position: Int)
        fun onUserInfoClick(position: Int)
    }

}