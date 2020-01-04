package com.example.hubbie.modules.login.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.example.hubbie.R
import com.example.hubbie.entities.UserLogin
import com.example.hubbie.modules.base.view.BaseFragment
import com.example.hubbie.modules.login.presenter.LoginPresenter
import kotlinx.android.synthetic.main.activity_main.*

class LoginFragment : BaseFragment(), View.OnClickListener {

    private lateinit var etId: EditText
    private lateinit var etPwd: EditText
    private lateinit var btnLogin: Button
    private lateinit var btnRegister: Button
    private lateinit var btnForgotAccount: Button
    private lateinit var presenter: LoginPresenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_login, main_container_frame, false)

        etId = rootView.findViewById(R.id.login_et_id)
        etPwd = rootView.findViewById(R.id.login_et_pwd)

        btnLogin = rootView.findViewById(R.id.login_btn_login)
        btnLogin.setOnClickListener(this)
        btnForgotAccount = rootView.findViewById(R.id.login_btn_forgot_account)
        btnForgotAccount.setOnClickListener(this)
        btnRegister = rootView.findViewById(R.id.login_btn_register)
        btnRegister.setOnClickListener(this)

        presenter = LoginPresenter(this)

        return rootView
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.login_btn_login -> {
                presenter.loginClicked(UserLogin(etId.text.toString(), etPwd.text.toString()))
            }
            R.id.login_btn_forgot_account -> {
                presenter.forgotAccountClicked()
            }
            R.id.login_btn_register -> {
                presenter.registerClicked()
            }
        }
    }

}