package com.example.lab03

import android.os.Parcel
import android.os.Parcelable

class TaskModel(val id: Int, var name: String, var description: String) : Parcelable {
    constructor(parcel: Parcel) : this (
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    ) {

    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(description)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TaskModel> {
        override fun createFromParcel(parcel: Parcel): TaskModel {
            return TaskModel(parcel)
        }

        override fun newArray(size: Int): Array<TaskModel?> {
            return arrayOfNulls(size)
        }
    }

}