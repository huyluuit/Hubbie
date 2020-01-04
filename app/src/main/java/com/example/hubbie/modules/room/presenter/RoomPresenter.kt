package com.example.hubbie.modules.room.presenter

import com.example.hubbie.entities.Room
import com.example.hubbie.modules.base.BaseContract
import com.example.hubbie.modules.room.IRoom
import com.example.hubbie.modules.room.interactor.RoomInteractor
import com.example.hubbie.modules.room.route.RoomRoute
import com.example.hubbie.modules.room.view.RoomFragment

class RoomPresenter(private val fragment: RoomFragment) : BaseContract.BasePresenter,
    IRoom.Presenter, IRoom.Interactor.Callbacks {

    private var interactor: RoomInteractor? = RoomInteractor(fragment, this)
    private var router: RoomRoute? = RoomRoute(fragment)
    private var view: IRoom.View? = null
    private val roomList = ArrayList<Room>()
    override fun setView(view: IRoom.View) {
        this.view = view
    }

    override fun onSw1StateChange(position: Int, result: Boolean) {
        interactor?.onSw1Change(roomList[position].deviceId.toString(), result)
    }

    override fun onSw2StateChange(position: Int, result: Boolean) {
        interactor?.onSw2Change(roomList[position].deviceId.toString(), result)
    }

    override fun onSw3StateChange(position: Int, result: Boolean) {
        interactor?.onSw3Change(roomList[position].deviceId.toString(), result)
    }

    override fun onItemCliked(position: Int) {
        router?.navigateToRoomDetail(roomList[position])
    }

    override fun itemControlClicked(controlId: Int) {
    }

    override fun onRoomChange(room: Room) {
        var p = 0
        for (item in roomList) {
            if (room.id == item.id) {
                break
            }
            p++
        }
        roomList.set(p, room)
        view?.onRoomChange(p)
    }

    override fun onEditRoomClicked(position: Int) {

    }

    override fun onBaseRoom(roomList: ArrayList<Room>) {
        this.roomList.addAll(roomList)
        view?.setBaseRoom(this.roomList)
    }

    override fun getBaseRooms() {
        interactor?.getBaseRooms()
    }

    override fun onDestroy() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}