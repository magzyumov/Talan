package ru.magzyumov.talan.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import ru.magzyumov.talan.data.Todo
import ru.magzyumov.talan.repository.TodoRepository
import ru.magzyumov.talan.utils.ResourceManager

import javax.inject.Inject

class ListViewModel @Inject constructor(
        private val todoRepository: TodoRepository,
        private val resourceManager: ResourceManager
) : ViewModel() {

        private val disposable = CompositeDisposable()

        private val passedTodoList: MutableLiveData<List<Todo>> = MutableLiveData()
        fun getPassedTodoList(): LiveData<List<Todo>> = passedTodoList

        private val todoList: MutableLiveData<List<Todo>> = MutableLiveData()
        fun getTodoList(): LiveData<List<Todo>> = todoList

        private val toaster: MutableLiveData<String> = MutableLiveData()
        fun getToaster(): LiveData<String> = toaster

        fun refreshLists(){
                refreshTodoLists(true)
                refreshTodoLists(false)
        }

        fun delete(todo: Todo){
                disposable.add(
                        todoRepository.delete(
                                todo,
                                {
                                        toaster.postValue(resourceManager.getDeletedString())
                                        refreshLists()
                                },
                                {
                                        toaster.postValue(it)
                                }
                        )
                )
        }

        fun update(
                todo: Todo
        ){
                disposable.add(
                        todoRepository.update(
                                todo,
                                {
                                        toaster.postValue(resourceManager.getSuccessString())
                                        refreshLists()
                                },
                                {
                                        toaster.postValue(it)
                                }
                        )
                )
        }


        private fun refreshTodoLists(passed: Boolean){
                disposable.add(
                        todoRepository.getTodoByPassed(
                                passed,
                                {
                                        when(passed){
                                                true -> passedTodoList.postValue(it)
                                                else -> todoList.postValue(it)
                                        }
                                },
                                {
                                        toaster.postValue(resourceManager.getEmptyString())
                                },
                                {
                                        toaster.postValue(it)
                                }
                        )
                )
        }


        override fun onCleared() {
                super.onCleared()
                disposable.dispose()
        }
}