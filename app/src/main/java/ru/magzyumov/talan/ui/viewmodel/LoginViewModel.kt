package ru.magzyumov.talan.ui.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import ru.magzyumov.talan.data.User
import ru.magzyumov.talan.repository.TodoRepository
import ru.magzyumov.talan.utils.Constants.Preferences.Companion.JWM_TOKEN
import ru.magzyumov.talan.utils.PreferenceHelper
import ru.magzyumov.talan.utils.ResourceManager
import javax.inject.Inject


class LoginViewModel @Inject constructor(
        private val todoRepository: TodoRepository,
        private val preferenceHelper: PreferenceHelper,
        private val resourceManager: ResourceManager
) : ViewModel() {

    private val disposable = CompositeDisposable()

    private val toaster: MutableLiveData<String> = MutableLiveData()
    fun getToaster(): LiveData<String> = toaster

    fun login(
            login: String,
            password: String,
            onSuccess: () -> Unit
    ){
        disposable.add(
                todoRepository.getUserByLoginPassword(
                        login,
                        password,
                        {
                            preferenceHelper.setStringPreference(JWM_TOKEN, it.hashCode().toString())
                            onSuccess.invoke()
                        },
                        {
                            toaster.postValue(resourceManager.getLoginFaultString())
                        },
                        {
                            toaster.postValue(it)
                        }
                )
        )
    }

    fun registration(
            name: String,
            login: String,
            password: String,
            onSuccess: () -> Unit
    ){
        val user = User(login = login, password = password, name = name)

        disposable.add(
                todoRepository.insertUser(
                        user,
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