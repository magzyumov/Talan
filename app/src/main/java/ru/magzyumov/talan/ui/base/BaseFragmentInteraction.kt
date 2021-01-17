package ru.magzyumov.talan.ui.base

interface BaseFragmentInteraction {
    fun <T> changeActivity(activityClass: Class<T>)
    fun showToast(message: String)
    fun changePageTitle(title: String)
}