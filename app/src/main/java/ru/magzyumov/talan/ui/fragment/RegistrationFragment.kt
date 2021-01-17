package ru.magzyumov.talan.ui.fragment


import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.magzyumov.talan.R
import ru.magzyumov.talan.databinding.FragmentLoginBinding
import ru.magzyumov.talan.databinding.FragmentRegistrationBinding
import ru.magzyumov.talan.ui.base.BaseFragment
import ru.magzyumov.talan.ui.viewmodel.LoginViewModel
import ru.magzyumov.talan.utils.PreferenceHelper
import javax.inject.Inject

class RegistrationFragment: BaseFragment(R.layout.fragment_registration) {
    override val binding by viewBinding(FragmentRegistrationBinding::bind)
    override val viewModel by viewModels<LoginViewModel> {viewModelProviderFactory}

    @Inject
    lateinit var preferenceHelper: PreferenceHelper

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnRegistration.setOnClickListener {
            registration()
        }

        binding.tvLogin.setOnClickListener {
            findNavController().popBackStack()
        }

        observerLiveData()
    }

    private fun registration(){
        if (isValid()){
            viewModel.registration(
                    binding.etName.text.toString(),
                    binding.etLogin.text.toString(),
                    binding.etPassword.text.toString()
            ) {
                findNavController().navigate(R.id.navigation_login)
            }
        } else{
            fragmentInteraction.showToast(getString(R.string.wrong_registration_data))
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
        return !(binding.etName.text.isNullOrEmpty()
                || binding.etLogin.text.isNullOrEmpty()
                || binding.etPassword.text.isNullOrEmpty()
                || binding.etRepeatPassword.text.isNullOrEmpty()
                || binding.etPassword.text.toString() != binding.etRepeatPassword.text.toString())
    }
}