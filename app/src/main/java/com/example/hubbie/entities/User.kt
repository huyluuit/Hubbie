package com.example.hubbie.entities

import android.os.Parcel
import android.os.Parcelable

data class User(
    var isActive: Boolean? = false,
    var uid: String? = "",
    val email: String? = "",
    var phone: String? = "",
    var fullName: String? = "",
    var pwd: String? = "",
    var role: String? = "",
    var adminId: String? = "",
    var roomId: String? = ""
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(isActive)
        parcel.writeString(uid)
        parcel.writeString(email)
        parcel.writeString(phone)
        parcel.writeString(fullName)
        parcel.writeString(pwd)
        parcel.writeString(role)
        parcel.writeString(adminId)
        parcel.writeString(roomId)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<User> {
        override fun createFromParcel(parcel: Parcel): User {
            return User(parcel)
        }

        override fun newArray(size: Int): Array<User?> {
            return arrayOfNulls(size)
        }
    }

}
