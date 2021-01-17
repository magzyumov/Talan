package ru.magzyumov.talan.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import ru.magzyumov.talan.utils.Constants.Preferences.Companion.GENERAL_PREFERENCES
import ru.magzyumov.talan.utils.PreferenceHelper
import ru.magzyumov.talan.database.TodoDao
import ru.magzyumov.talan.database.TodoDatabase
import ru.magzyumov.talan.repository.TodoRepository
import ru.magzyumov.talan.utils.ResourceManager
import javax.inject.Singleton


@Module
class AppModule {

    @Provides
    fun provideSharedPreferences(application: Application): PreferenceHelper {
        return PreferenceHelper(application.getSharedPreferences(GENERAL_PREFERENCES, Context.MODE_PRIVATE))
    }

    @Provides
    fun provideResourceManager(application: Application): ResourceManager {
        return ResourceManager(application)
    }

    @Singleton
    @Provides
    fun providesAppDatabase(application: Application): TodoDatabase {
        return Room.databaseBuilder(application, TodoDatabase::class.java, "todo_database").build()
    }

    @Singleton
    @Provides
    fun providesNoteDao(database: TodoDatabase): TodoDao {
        return database.todoDao()
    }

    @Provides
    fun providesRepository(todoDao: TodoDao): TodoRepository {
        return TodoRepository(todoDao)
    }


}