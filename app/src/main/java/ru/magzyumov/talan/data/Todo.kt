package ru.magzyumov.talan.data


import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Todo(
    val id: Int = 0,
    var title: String? = null,
    var description: String? = null,
    var image: String? = null,
    var date: Date = Date(),
    var passed: Boolean = false

): Parcelable {

}