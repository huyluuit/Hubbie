package com.example.hubbie.modules.register.route

import androidx.fragment.app.Fragment
import com.example.hubbie.modules.main.view.MainFragment
import com.example.hubbie.modules.register.IRegister

class RegisterRoute(private val fragment: Fragment?) : IRegister.Route {

    override fun navigateToLogin() {
        fragment?.fragmentManager?.popBackStack()
    }

    override fun navigateToRoom() {
        fragment?.fragmentManager?.beginTransaction()?.apply {
            replace(fragment.id, MainFragment())
            disallowAddToBackStack()
        }?.commit()
    }

}