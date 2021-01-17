package ru.magzyumov.talan

import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import ru.magzyumov.talan.di.AppComponent
import ru.magzyumov.talan.di.DaggerAppComponent


class App: DaggerApplication() {


    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().application(this).build()
    }

}