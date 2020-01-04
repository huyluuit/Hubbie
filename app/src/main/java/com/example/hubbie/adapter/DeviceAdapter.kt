package com.example.hubbie.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hubbie.R
import com.example.hubbie.entities.Device

class DeviceAdapter(private val data: ArrayList<Device>, private val listener: OnItemClick) :
    RecyclerView.Adapter<DeviceAdapter.DataViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        return DataViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_device,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            holder.bindOnItemClick(position, listener)
        }
        holder.tvDeviceId.text = data[position].id
        holder.tvTemp.text = "NHIỆT ĐỘ: " + data[position].temp.toString() + "°C"
        holder.tvTotalPower.text = "CÔNG SUẤT: " + data[position].totalPower.toString() + "W"
    }


    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvDeviceId: TextView
        var tvTemp: TextView
        var tvTotalPower: TextView
        var tvRoomNameBelong: TextView

        init {
            tvDeviceId = itemView.findViewById(R.id.tvDeviceId)
            tvTemp = itemView.findViewById(R.id.tvTemp)
            tvTotalPower = itemView.findViewById(R.id.tvTotalPower)
            tvRoomNameBelong = itemView.findViewById(R.id.tvRoomNameBelongDevice)
        }

        fun bindOnItemClick(position: Int, listener: OnItemClick) {
            listener.onItemClick(position)
        }

    }

    interface OnItemClick {
        fun onItemClick(position: Int)
    }

}