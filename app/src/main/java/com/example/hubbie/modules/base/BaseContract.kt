package com.example.hubbie.modules.base

interface BaseContract {

    interface BaseView {
        fun showLoading()
        fun dismissLoading()
        fun showProcessLoading(message: String)
        fun dismissProcessLoading()
    }

    interface BasePresenter {
        fun onDestroy()
    }

    interface BaseInteractor {
        fun onDestroy()
    }

    interface BaseRouter

}