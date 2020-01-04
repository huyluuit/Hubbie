package com.example.hubbie.modules.room.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hubbie.R
import com.example.hubbie.adapter.RoomAdapter
import com.example.hubbie.entities.Room
import com.example.hubbie.modules.base.view.BaseFragment
import com.example.hubbie.modules.dialog.AddRoomFragmentDialog
import com.example.hubbie.modules.main.IMain
import com.example.hubbie.modules.room.IRoom
import com.example.hubbie.modules.room.presenter.RoomPresenter

class RoomFragment(private val mainCallbacks: IMain.View?) : BaseFragment(), IRoom.View,
    RoomAdapter.OnItemClickListener, AddRoomFragmentDialog.EditRoomDialogCallbacks {

    private var roomList = ArrayList<Room>()
    private var roomAdapter: RoomAdapter? = null
    private var presenter: RoomPresenter? = null
    private lateinit var rvRoomList: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val v = inflater.inflate(R.layout.fragment_room, container, false)
        presenter = RoomPresenter(this)
        presenter?.setView(this)
        presenter?.getBaseRooms()
        rvRoomList = v.findViewById(R.id.rvRoom)
        rvRoomList.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        return v
    }

    override fun createRoomClicked() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setBaseRoom(roomList: ArrayList<Room>) {
        this.roomList = roomList
        roomAdapter = RoomAdapter(this.roomList, this)
        rvRoomList.adapter = roomAdapter
    }

    override fun onItemClick(position: Int) {
        mainCallbacks?.onRoomItemClicked(roomList[position])
    }

    override fun onSw1Click(position: Int, result: Boolean) {
        presenter?.onSw1StateChange(position, result)
    }

    override fun onSw2Click(position: Int, result: Boolean) {
        presenter?.onSw2StateChange(position, result)
    }

    override fun onSw3Click(position: Int, result: Boolean) {
        presenter?.onSw3StateChange(position, result)
    }

    override fun onEditRoom(position: Int) {
        fragmentManager?.beginTransaction()
            ?.add(
                AddRoomFragmentDialog.newInstance(roomList[position], this) as Fragment,
                AddRoomFragmentDialog::class.java.simpleName
            )
            ?.commitAllowingStateLoss()
    }

    override fun onRoomChange(position: Int) {
        roomAdapter?.notifyItemChanged(position)
    }

    override fun onDeleteRoom(room: Room) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onSaveRoom(room: Room) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}