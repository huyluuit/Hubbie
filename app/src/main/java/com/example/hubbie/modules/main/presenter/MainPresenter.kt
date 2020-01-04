package com.example.hubbie.modules.main.presenter

import com.example.hubbie.entities.Device
import com.example.hubbie.entities.Room
import com.example.hubbie.entities.User
import com.example.hubbie.modules.main.IMain
import com.example.hubbie.modules.main.route.MainRoute
import com.example.hubbie.modules.main.view.MainFragment

class MainPresenter(fragment: MainFragment) : IMain.Presenter {

    val router = MainRoute(fragment)

    override fun onMyInfoClicked() {

    }

    override fun onChatClick() {

    }

    override fun createRoomClicked() {

    }

    override fun onNewRoomCreated(room: Room) {

    }

    override fun onItemRoomClicked(room: Room) {

    }

    override fun onItemUserClicked(user: User) {

    }

    override fun onItemDeviceClicked(device: Device) {

    }

    fun navigationUserInfo() {
        router.navigationUserInfor()
    }

    fun navigationListChat() {
        router.navigationListChat()
    }

    override fun onDestroy() {

    }


}
