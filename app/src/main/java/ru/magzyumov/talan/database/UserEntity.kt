package ru.magzyumov.talan.database


import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.magzyumov.talan.data.Todo
import ru.magzyumov.talan.data.User


@Entity(tableName = "tbl_user")
class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    var login: String,
    var password: String,
    val name: String
){
    constructor(user: User): this (
            user.id,
            user.login,
            user.password,
            user.name
    )
}