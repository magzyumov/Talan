package ru.magzyumov.talan.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import ru.magzyumov.talan.data.Todo
import ru.magzyumov.talan.repository.TodoRepository
import ru.magzyumov.talan.utils.ResourceManager

import javax.inject.Inject

class AddViewModel @Inject constructor(
        private val todoRepository: TodoRepository,
        private val resourceManager: ResourceManager
) : ViewModel() {

        private val disposable = CompositeDisposable()

        private val toaster: MutableLiveData<String> = MutableLiveData()
        fun getToaster(): LiveData<String> = toaster

        fun save(
                todo: Todo,
                onSuccess: () -> Unit,
        ){
                disposable.add(
                        todoRepository.insert(
                                todo,
                                {
                                        toaster.postValue(resourceManager.getSuccessString())
                                        onSuccess.invoke()
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