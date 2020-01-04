package com.example.hubbie.entities

import android.os.Parcel
import android.os.Parcelable

data class Device(
    var id: String? = "",
    var ip: String? = "",
    var isMaster: Boolean? = false,
    var portAState: Boolean? = false,
    var portBState: Boolean? = false,
    var portCState: Boolean? = false,
    var powerA: Float? = 0f,
    var powerB: Float? = 0f,
    var powerC: Float? = 0f,
    var totalPower: Float? = 0f,
    val temp: Float? = 0f
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
        parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
        parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
        parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
        parcel.readValue(Float::class.java.classLoader) as? Float,
        parcel.readValue(Float::class.java.classLoader) as? Float,
        parcel.readValue(Float::class.java.classLoader) as? Float,
        parcel.readValue(Float::class.java.classLoader) as? Float,
        parcel.readValue(Float::class.java.classLoader) as? Float
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(ip)
        parcel.writeValue(isMaster)
        parcel.writeValue(portAState)
        parcel.writeValue(portBState)
        parcel.writeValue(portCState)
        parcel.writeValue(powerA)
        parcel.writeValue(powerB)
        parcel.writeValue(powerC)
        parcel.writeValue(totalPower)
        parcel.writeValue(temp)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Device> {
        override fun createFromParcel(parcel: Parcel): Device {
            return Device(parcel)
        }

        override fun newArray(size: Int): Array<Device?> {
            return arrayOfNulls(size)
        }
    }

}

data class DeviceSorted(
    var macAddress: String = "",
    var ipAddress: String = ""
)