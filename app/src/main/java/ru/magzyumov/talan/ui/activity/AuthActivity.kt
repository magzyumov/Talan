package ru.magzyumov.talan.ui.activity


import android.os.Bundle
import ru.magzyumov.talan.databinding.ActivityLoginBinding
import ru.magzyumov.talan.ui.base.BaseActivity


class AuthActivity: BaseActivity() {
    override val binding by viewBinding(ActivityLoginBinding::inflate)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

}