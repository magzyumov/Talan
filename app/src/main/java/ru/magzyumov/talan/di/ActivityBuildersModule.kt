package ru.magzyumov.talan.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import ru.magzyumov.talan.ui.activity.AuthActivity
import ru.magzyumov.talan.ui.activity.MainActivity
import ru.magzyumov.talan.ui.activity.SplashActivity


@Module
abstract class ActivityBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeSplashActivity(): SplashActivity

    @ContributesAndroidInjector(modules = [FragmentBuildersModule::class, ViewModelModule::class])
    abstract fun contributeAuthActivity(): AuthActivity

    @ContributesAndroidInjector(modules = [FragmentBuildersModule::class, ViewModelModule::class])
    abstract fun contributeMainActivity(): MainActivity
}