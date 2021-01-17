package ru.magzyumov.talan.database


import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.magzyumov.talan.data.Todo
import ru.magzyumov.talan.utils.formatToString
import java.util.*


@Entity(tableName = "tbl_todo")
class TodoEntity(
        @PrimaryKey(autoGenerate = true)
        val id: Int,
        val title: String,
        val description: String,
        val image: String,
        val date: Long = Date().time,
        val passed: Boolean = false
) {
        constructor(todo: Todo): this (
                todo.id,
                todo.title.orEmpty(),
                todo.description.orEmpty(),
                todo.image.orEmpty(),
                todo.date.time,
                todo.passed
        )
}