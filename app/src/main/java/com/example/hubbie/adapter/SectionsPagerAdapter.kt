package com.example.hubbie.adapter

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.hubbie.modules.device.view.DeviceFragment
import com.example.hubbie.modules.main.IMain
import com.example.hubbie.modules.room.view.RoomFragment
import com.example.hubbie.modules.user.view.UserFragment

class SectionsPagerAdapter(fm: FragmentManager, pages: Int, private val listener: IMain.View) :
    FragmentPagerAdapter(fm) {

    //Page of user. It depend on user permission
    val pages = pages
    private val TAG = "SectionsPagerAdapter"
    override fun getItem(p0: Int): Fragment {
        Log.e(TAG, "getItem number: " + p0)
        return when (p0) {
            0 -> RoomFragment(listener)
            1 -> UserFragment(listener)
            2 -> DeviceFragment(listener)
            else -> RoomFragment(listener)
        }
    }

    override fun getCount(): Int {
        return pages
    }

}