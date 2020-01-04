package com.example.hubbie.modules.main

import com.example.hubbie.entities.Device
import com.example.hubbie.entities.Room
import com.example.hubbie.entities.User
import com.example.hubbie.modules.base.BaseContract

interface IMain {
    interface Presenter : BaseContract.BasePresenter {
        fun onMyInfoClicked()
        fun onChatClick()
        fun createRoomClicked()
        fun onNewRoomCreated(room: Room)
        fun onItemRoomClicked(room: Room)
        fun onItemUserClicked(user: User)
        fun onItemDeviceClicked(device: Device)
    }

    interface Interactor : BaseContract.BaseInteractor {
        fun addNewRoom(room: Room)
        fun doAccountChangeListener(userId: String)
        interface Callbacks {
            fun onAccountDisable()
            fun onAccountDelete()
            fun onAccountUpdateNewChanges()
        }
    }

    interface Route : BaseContract.BaseRouter {
        fun navigateToLogin()
        fun navigateToAccountInfo()
        fun navigateToRoomDetail(room: Room)
        fun navigateToUserDetail(user: User)
        fun navigateToDeviceDetail(device: Device)
        fun navigateToChat()
    }

    interface View {
        fun onRoomItemClicked(room: Room)
        fun onUserItemClicked(user: User)
        fun onDeviceItemClicked(device: Device)
    }
}