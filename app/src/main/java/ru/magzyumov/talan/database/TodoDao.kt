package ru.magzyumov.talan.database

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Maybe


@Dao
interface TodoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTodo(todo: TodoEntity): Completable

    @Update
    fun updateTodo(note: TodoEntity): Completable

    @Query("delete from tbl_todo where id = :id")
    fun deleteTodoById(id: Int): Completable

    @Delete
    fun deleteTodo(note: TodoEntity): Completable

    @Query("select * from tbl_todo where passed = :passed order by date desc")
    fun getTodoByPassed(passed: Boolean): Maybe<List<TodoEntity>>



    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: UserEntity): Completable

    @Query("select * from tbl_user where login = :login and password = :password")
    fun getUserByLoginPassword(login: String, password: String): Maybe<UserEntity>

}