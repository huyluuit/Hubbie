package com.example.hubbie.modules.account.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.hubbie.R
import com.example.hubbie.modules.account.IAccount
import com.example.hubbie.modules.account.presenter.AccountPresenter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_my_info.view.*

class AccountFragment : Fragment(), IAccount.View, View.OnClickListener {

    private var getView: View? = null
    private var presenter: AccountPresenter? = null
    private var user: com.example.hubbie.entities.User? = null

    companion object {
        fun newInstance(): AccountFragment {
            val fragment = AccountFragment()
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        getView = inflater.inflate(R.layout.fragment_my_info, main_container_frame, false)
        presenter = AccountPresenter(context)
        getUserData()
        initView()
        return getView
    }

    private fun getUserData() {
        user = presenter?.getUser()
    }

    private fun initView() {
        getView?.my_info_tv_name_display?.text = user?.fullName
        getView?.my_info_tv_email?.text = user?.email
        getView?.my_info_tv_phone?.text = user?.phone
        getView?.my_info_tv_rule?.text = user?.role
        if (user?.isActive == true) {
            getView?.my_info_tv_state?.text = "ACTIVE"
        } else {
            getView?.my_info_tv_state?.text = "NOT ACTIVE"
        }

    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.my_info_btn_pwd_change -> {
                //Change password
            }
            R.id.my_info_btn_back -> {
                //Back to previous screen
            }
        }
    }
}