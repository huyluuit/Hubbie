/*
 * (C) Copyright 2008 STYL Solutions Pte. Ltd. , All rights reserved
 * This source code and any compilation or derivative thereof is the sole
 * property of STYL Solutions Pte. Ltd. and is provided pursuant to a
 * Software License Agreement.  This code is the proprietary information of
 * STYL Solutions Pte. Ltd. and is confidential in nature. Its use and
 * dissemination by any party other than STYL Solutions Pte. Ltd. is strictly
 * limited by the confidential information provisions of the Agreement
 * referenced above.
 */

package com.example.hubbie.modules.base.view

import androidx.fragment.app.Fragment
import com.example.hubbie.modules.base.BaseContract
import com.example.hubbie.modules.dialog.LoadingFragment

/**
 * Created by trangpham on 16/8/2019.
 */
open class BaseFragment : Fragment(), BaseContract.BaseView {
    override fun showProcessLoading(message: String) {

    }

    override fun dismissProcessLoading() {

    }

    private var loadingFragment: LoadingFragment? = null

    override fun onResume() {
        super.onResume()

        dismissLoading()
    }

    override fun showLoading() {
        if (loadingFragment == null) {
            loadingFragment = LoadingFragment()
            fragmentManager?.beginTransaction()
                ?.add(loadingFragment as Fragment, LoadingFragment::class.java.simpleName)
                ?.commitAllowingStateLoss()
        }
    }

    override fun dismissLoading() {
        loadingFragment?.dismissAllowingStateLoss()
        loadingFragment = null
    }
}