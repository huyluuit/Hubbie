package com.example.hubbie.modules.user

import com.example.hubbie.entities.User

interface IUser {
    interface Presenter {
        fun userItemClicked(userId: Int)
        fun functionUserItemClicked(functionType: Int)
        fun createUserClicked()
        fun getAllUsers(): ArrayList<User>
    }

    interface Interactor {
        fun getUser(userId: Int): User
        fun getAllUsers(): ArrayList<User>
    }

    interface Route {
        fun navigateToLogin()
        fun navigateToAccountInfo()
        fun navigateToCreateUser()
        fun navigateToUserDetail()
        fun navigateToChat()
    }

    interface View {
        fun onItemClicked()
        fun createUserClicked()
    }
}