package ru.magzyumov.talan.di

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import ru.magzyumov.talan.utils.ViewModelProviderFactory


@Module
abstract class ViewModelFactoryModule {

    @Binds
    abstract fun bindViewModelFactory(viewModelProvideFactory: ViewModelProviderFactory): ViewModelProvider.Factory
}