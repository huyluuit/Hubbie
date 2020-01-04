package com.example.hubbie.utilis.firestore

import com.example.hubbie.entities.DeviceSorted
import com.example.hubbie.utilis.ConvertDataUtils
import com.google.firebase.firestore.FirebaseFirestore
import io.reactivex.Completable
import io.reactivex.Single

object FirestoreDeviceUtil {

    const val COLLECTION_DEVICE_LIST = "DeviceList"
    private val db = FirebaseFirestore.getInstance().collection(FirestoreUserUtil.COLLECTION_USER)

    fun getAllDeviceList(uid: String): Single<ArrayList<DeviceSorted>> {

        return Single.create {
            db.document(uid).collection(COLLECTION_DEVICE_LIST).get()
                .addOnCompleteListener { task ->

                    if (task.isSuccessful && task.result != null) {
                        val result = ArrayList<DeviceSorted>()
                        result.clear()
                        for (item in task.result!!.documents) {
                            val deviceSorted = ConvertDataUtils.convertDataToDeviceSort(item.data)
                            result.add(deviceSorted)
                        }
                        it.onSuccess(result)
                    } else {
                        return@addOnCompleteListener
                    }

                }
        }

    }

    fun setDevice(uid: String, device: DeviceSorted): Completable {

        return Completable.create {

            db.document(uid).collection(COLLECTION_DEVICE_LIST).document(device.macAddress)
                .set(device).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        it.onComplete()
                    } else {
                        return@addOnCompleteListener
                    }
                }
        }

    }

}