package ru.magzyumov.talan.repository


import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import ru.magzyumov.talan.data.Todo
import ru.magzyumov.talan.data.User
import ru.magzyumov.talan.database.TodoEntity
import ru.magzyumov.talan.database.TodoDao
import ru.magzyumov.talan.database.UserEntity
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class TodoRepository @Inject constructor(
    private val todoDao: TodoDao
) {

    fun insert(
        todo: Todo,
        onComplete: () -> Unit,
        onError: (String) -> Unit
    ): Disposable {
        return todoDao.insertTodo(TodoEntity(todo))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {onComplete.invoke()},
                {onError.invoke(it.localizedMessage.orEmpty())}
            )
    }

    fun insertUser(
            user: User,
            onComplete: () -> Unit,
            onError: (String) -> Unit
    ): Disposable {
        return todoDao.insertUser(UserEntity(user))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {onComplete.invoke()},
                        {onError.invoke(it.localizedMessage.orEmpty())}
                )
    }

    fun getUserByLoginPassword(
            login: String,
            password: String,
            onSuccess: (User) -> Unit,
            onComplete: () -> Unit,
            onError: (String) -> Unit,
    ): Disposable {
        return todoDao.getUserByLoginPassword(login, password)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
                .map(parseUserFromDb)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {onSuccess.invoke(it)},
                        {onError.invoke(it.localizedMessage.orEmpty())},
                        {onComplete.invoke()}
                )
    }


    fun update(
        todo: Todo,
        onComplete: () -> Unit,
        onError: (String) -> Unit
    ): Disposable {
        return todoDao.updateTodo(TodoEntity(todo))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {onComplete.invoke()},
                {onError.invoke(it.localizedMessage.orEmpty())}
            )
    }

    fun delete(
        todo: Todo,
        onComplete: () -> Unit,
        onError: (String) -> Unit
    ): Disposable {
        return todoDao.deleteTodo(TodoEntity(todo))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {onComplete.invoke()},
                {onError.invoke(it.localizedMessage.orEmpty())}
            )
    }

    fun deleteById(
        id: Int,
        onComplete: () -> Unit,
        onError: (String) -> Unit
    ): Disposable {
        return todoDao.deleteTodoById(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {onComplete.invoke()},
                {onError.invoke(it.localizedMessage.orEmpty())}
            )
    }

    fun getTodoByPassed(
            passsed: Boolean,
            onSuccess: (List<Todo>) -> Unit,
            onComplete: () -> Unit,
            onError: (String) -> Unit,
    ): Disposable {
        return todoDao.getTodoByPassed(passsed)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.computation())
            .map(parseTodoFromDb)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {onSuccess.invoke(it)},
                {onError.invoke(it.localizedMessage.orEmpty())},
                {onComplete.invoke()}
            )
    }

    private val parseTodoFromDb: Function1<List<TodoEntity>, List<Todo>> =
        { todoFromDb: List<TodoEntity> ->
            val result: MutableList<Todo> = ArrayList()
            todoFromDb.forEach {todo ->
                result.add(Todo(
                    todo.id,
                    todo.title,
                    todo.description,
                    if(todo.image.isEmpty()){null} else {todo.image},
                    Date(todo.date),
                    todo.passed
                ))
            }
            result
        }

    private val parseUserFromDb: Function1<UserEntity, User> =
            { userFromDb: UserEntity ->
                User(
                        userFromDb.id,
                        userFromDb.login,
                        userFromDb.password,
                        userFromDb.name
                )
            }

}