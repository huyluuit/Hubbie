package com.example.hubbie.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hubbie.R

class DeviceIdAdapter(
    private val data: ArrayList<Pair<String, String>>,
    private val listener: Callbacks
) : RecyclerView.Adapter<DeviceIdAdapter.DataViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        return DataViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_sample,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {

        holder.tvFirstValue.text = data[position].first
        holder.tvSecondValue.text = data[position].second
        holder.itemView.setOnClickListener {
            holder.bindItemClick(position, listener)
        }

    }

    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val tvFirstValue: TextView
        val tvSecondValue: TextView

        init {
            tvFirstValue = itemView.findViewById(R.id.tvFirstValue)
            tvSecondValue = itemView.findViewById(R.id.tvSecondValue)
        }

        fun bindItemClick(position: Int, listener: Callbacks) {
            listener.onItemClick(position)
        }

    }

    interface Callbacks {
        fun onItemClick(position: Int)
    }

}