package com.example.hubbie.utilis.firestore

import com.example.hubbie.entities.Room
import com.example.hubbie.entities.RoomUser
import com.example.hubbie.entities.throwable.UserThrowable
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import io.reactivex.Completable
import io.reactivex.Single

object FirestoreRoomUtil {

    private const val COLLECTION_ROOM = "Room"
    private const val COLLECTION_ROOM_CONTROL = "RoomControl"
    private const val COLLECTION_USER = "User"
    private const val USER_NULL = com.example.hubbie.BuildConfig.APPLICATION_ID + ".USER_IS_NULL"
    private const val USER_DISABLE =
        com.example.hubbie.BuildConfig.APPLICATION_ID + ".USER_HAS_DISABLE"
    private val db = FirebaseFirestore.getInstance().collection(COLLECTION_ROOM)
    private val userRoom = FirebaseFirestore.getInstance().collection(COLLECTION_USER)

    fun getBaseRoom(userId: String): Single<ArrayList<Room>> {
        return Single.create {
            userRoom.document(userId).collection(
                COLLECTION_ROOM_CONTROL
            ).get().addOnCompleteListener { task: Task<QuerySnapshot> ->
                if (task.isSuccessful && task.result != null) {
                    val roomList = ArrayList<Room>()
                    if (roomList.size > 0) {
                        roomList.clear()
                    }
                    val docs = task.result!!.documents
                    for (item in docs) {
                        val roomControl = item.toObject(RoomUser::class.java)
                        if (roomControl != null) {
                            if (roomControl.validate!!) {
                                db.document(roomControl.id.toString()).get()
                                    .addOnCompleteListener { task1 ->
                                        if (task1.isSuccessful && task.result != null) {
                                            val room = task1.result!!.toObject(Room::class.java)
                                            if (room != null) {
                                                roomList.add(room)
                                                if (room.id == docs.last().id) {
                                                    it.onSuccess(roomList)
                                                }
                                            }
                                        }
                                    }
                            } else {
                                if (item.id == docs.last().id) {
                                    it.onSuccess(roomList)
                                }
                                it.onError(UserThrowable(USER_DISABLE))
                            }
                        }
                    }
                }
            }
        }
    }

    fun getRoom(roomId: String): Single<Room> {
        return Single.create {
            db.document(roomId).get().addOnCompleteListener { task ->
                if (task.isSuccessful && task.result != null) {
                    val room = task.result!!.toObject(Room::class.java)
                    if (room != null) {
                        it.onSuccess(room)
                    }
                }
            }
        }
    }

    fun setRoom(room: Room?): Completable {
        return Completable.create {
            if (room != null) {
                db.document()
            }
        }
    }

}