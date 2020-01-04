package com.example.hubbie.utilis.realtime

import android.util.Log
import com.example.hubbie.entities.Device
import com.example.hubbie.utilis.firestore.FirestoreDeviceUtil
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

object FirebaseRealtimeDevice {

    private val db = FirebaseDatabase.getInstance()
    private val ref = db.getReference(FirestoreDeviceUtil.COLLECTION_DEVICE_LIST)

    fun getBaseDevice(): Single<ArrayList<Device>> {
        return Single.create {

        }
    }

    fun setDevice(device: Device): Completable {
        return Completable.create {
            ref.child(device.id.toString()).setValue(device).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    it.onComplete()
                } else {
                    return@addOnCompleteListener
                }
            }
        }
    }

    fun getDevice(deviceId: String): Observable<Device> {
        return Observable.create {
            ref.child(deviceId).addValueEventListener(object : ValueEventListener {

                override fun onCancelled(p0: DatabaseError) {

                }

                override fun onDataChange(p0: DataSnapshot) {
                    val device = p0.getValue(Device::class.java)
                    if (device != null) {
                        it.onNext(device)
                    } else {
                        Log.e("GetDevice", "Device is null!")
                    }
                }

            })
        }
    }
}