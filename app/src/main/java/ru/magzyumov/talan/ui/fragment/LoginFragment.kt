package ru.magzyumov.talan.ui.fragment


import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.magzyumov.talan.R
import ru.magzyumov.talan.databinding.FragmentLoginBinding
import ru.magzyumov.talan.ui.activity.MainActivity
import ru.magzyumov.talan.ui.base.BaseFragment
import ru.magzyumov.talan.ui.viewmodel.LoginViewModel
import ru.magzyumov.talan.utils.PreferenceHelper
import javax.inject.Inject

class LoginFragment: BaseFragment(R.layout.fragment_login) {
    override val binding by viewBinding(FragmentLoginBinding::bind)
    override val viewModel by viewModels<LoginViewModel> {viewModelProviderFactory}

    @Inject
    lateinit var preferenceHelper: PreferenceHelper

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvRegistration.setOnClickListener {
            findNavController().navigate(R.id.navigation_registration)
        }

        binding.btnLogin.setOnClickListener {
            login()
        }

        observerLiveData()
    }

    private fun login(){
        if (isValid()){
            viewModel.login(
                    binding.etLogin.text.toString(),
                    binding.etPassword.text.toString()
            ) {
                fragmentInteraction.changeActivity(MainActivity::class.java)
            }

        } else {
            fragmentInteraction.showToast(getString(R.string.wrong_login_data))
        }
    }

    private fun observerLiveData() {
        viewModel.getToaster().observe(viewLifecycleOwner, {toast ->
            toast?.let {
                fragmentInteraction.showToast(it)
            }
        })
    }

    private fun isValid(): Boolean {
        return !(binding.etLogin.text.isNullOrEmpty()
                && binding.etPassword.text.isNullOrEmpty())
    }
}