package ru.magzyumov.talan.database

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [TodoEntity::class, UserEntity::class], version = 1)
abstract class TodoDatabase : RoomDatabase() {
    abstract fun todoDao(): TodoDao
}