package com.example.hubbie.modules.main.route

import com.example.hubbie.MainActivity
import com.example.hubbie.entities.Device
import com.example.hubbie.entities.Room
import com.example.hubbie.entities.User
import com.example.hubbie.entities.shared.AccountPreferences
import com.example.hubbie.modules.account.view.AccountFragment
import com.example.hubbie.modules.login.view.LoginFragment
import com.example.hubbie.modules.main.IMain
import com.example.hubbie.modules.main.view.MainFragment
import com.example.hubbie.modules.message.listChat.view.ListChatFragment


class MainRoute(private val fragment: MainFragment) : IMain.Route {

    fun navigationUserInfor() {
        (fragment.context as MainActivity).changeFragment(AccountFragment.newInstance())
    }

    fun navigationListChat() {
        (fragment.context as MainActivity).changeFragment(
            ListChatFragment.newInstance(
                AccountPreferences(fragment.context!!).getBaseAccount()
            )
        )
    }

    override fun navigateToRoomDetail(room: Room) {

    }

    override fun navigateToUserDetail(user: User) {

    }

    override fun navigateToDeviceDetail(device: Device) {

    }

    override fun navigateToLogin() {
        fragment.fragmentManager?.beginTransaction()?.apply {
            replace(fragment.id, LoginFragment())
            disallowAddToBackStack()
        }?.commit()
    }

    override fun navigateToAccountInfo() {
        fragment.fragmentManager?.beginTransaction()?.apply {
            add(fragment.id, AccountFragment())
            addToBackStack(AccountFragment()::class.java.simpleName)
        }?.commit()
    }

    override fun navigateToChat() {
//        fragment.fragmentManager?.beginTransaction()?.apply {
//            add(fragment.id, )
//            addToBackStack(AccountFragment()::class.java.simpleName)
//        }?.commit()
    }

}
