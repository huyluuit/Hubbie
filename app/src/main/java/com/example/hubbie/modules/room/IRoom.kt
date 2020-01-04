package com.example.hubbie.modules.room

import com.example.hubbie.entities.Room

interface IRoom {

    interface Presenter {
        fun setView(view: View)
        fun onSw1StateChange(position: Int, result: Boolean)
        fun onSw2StateChange(position: Int, result: Boolean)
        fun onSw3StateChange(position: Int, result: Boolean)
        fun itemControlClicked(controlId: Int)
        fun onItemCliked(position: Int)
        fun onEditRoomClicked(position: Int)
        fun getBaseRooms()
    }

    interface Interactor {
        fun getBaseRooms()
        fun onSw1Change(deviceId: String, state: Boolean)
        fun onSw2Change(deviceId: String, state: Boolean)
        fun onSw3Change(deviceId: String, state: Boolean)
        interface Callbacks {
            fun onBaseRoom(roomList: ArrayList<Room>)
            fun onRoomChange(room: Room)
        }
    }

    interface Route {
        fun navigateToCreateRoom()
        fun navigateToEditRoom(room: Room)
        fun navigateToRoomDetail(room: Room)
    }

    interface View {
        fun createRoomClicked()
        fun setBaseRoom(roomList: ArrayList<Room>)
        fun onRoomChange(position: Int)
    }
}