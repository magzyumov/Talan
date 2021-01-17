package ru.magzyumov.talan.utils

import android.app.Application
import ru.magzyumov.talan.R

import javax.inject.Inject

class ResourceManager @Inject constructor(
    private val application: Application
)  {

    fun getSuccessString(): String = application.getString(R.string.success)
    fun getDeletedString(): String = application.getString(R.string.deleted)
    fun getEmptyString(): String = application.getString(R.string.empty)
    fun getLoginFaultString(): String = application.getString(R.string.login_pass_not_fount)
}