package ru.magzyumov.talan.di


import dagger.Module
import dagger.android.ContributesAndroidInjector
import ru.magzyumov.talan.ui.fragment.AddFragment
import ru.magzyumov.talan.ui.fragment.ListFragment
import ru.magzyumov.talan.ui.fragment.LoginFragment
import ru.magzyumov.talan.ui.fragment.RegistrationFragment


@Module
abstract class FragmentBuildersModule {

    //Login
    @ContributesAndroidInjector
    abstract fun contributeLoginFragment(): LoginFragment

    @ContributesAndroidInjector
    abstract fun contributeRegistrationFragment(): RegistrationFragment

    //Main
    @ContributesAndroidInjector
    abstract fun contributeListFragment(): ListFragment

    @ContributesAndroidInjector
    abstract fun contributeAddFragment(): AddFragment

}