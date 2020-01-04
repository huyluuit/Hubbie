package com.example.hubbie.modules.register.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hubbie.R
import com.example.hubbie.modules.base.view.BaseFragment
import com.example.hubbie.modules.register.presenter.RegisterPresenter
import kotlinx.android.synthetic.main.fragment_register.view.*

class RegisterFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val v = inflater.inflate(R.layout.fragment_register, container, false)

        val presenter = RegisterPresenter(this)

        v?.btnRegister?.setOnClickListener {
            val email = v.etEmail?.text.toString()
            val phone = v.etPhone?.text.toString()
            val name = v.etFullname?.text.toString()
            val pwd = v.etPwd?.text.toString()
            val confirmPwd = v.etConfirmPwd?.text.toString()
            val adminId = v.etAdminId?.text.toString()
            presenter.signInUser(email, phone, name, pwd, confirmPwd, adminId)
        }

        v?.btnBack?.setOnClickListener {
            presenter.onBackClicked()
        }

        return v

    }

}