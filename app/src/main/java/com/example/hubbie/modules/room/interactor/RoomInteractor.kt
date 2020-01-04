package com.example.hubbie.modules.room.interactor

import com.example.hubbie.modules.room.IRoom
import com.example.hubbie.modules.room.view.RoomFragment
import io.reactivex.disposables.CompositeDisposable

class RoomInteractor(fragment: RoomFragment, callbacks: IRoom.Interactor.Callbacks) :
    IRoom.Interactor {

    private val compositeDisposable = CompositeDisposable()

    override fun getBaseRooms() {

    }

    override fun onSw1Change(deviceId: String, state: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onSw2Change(deviceId: String, state: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onSw3Change(deviceId: String, state: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}