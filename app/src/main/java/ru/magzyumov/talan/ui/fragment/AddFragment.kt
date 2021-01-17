package ru.magzyumov.talan.ui.fragment


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.magzyumov.talan.R
import ru.magzyumov.talan.data.Todo
import ru.magzyumov.talan.databinding.FragmentAddBinding
import ru.magzyumov.talan.ui.activity.AuthActivity
import ru.magzyumov.talan.ui.base.BaseFragment
import ru.magzyumov.talan.ui.dialog.ImageDialog
import ru.magzyumov.talan.ui.viewmodel.AddViewModel
import ru.magzyumov.talan.utils.Constants.Preferences.Companion.JWM_TOKEN
import ru.magzyumov.talan.utils.Constants.RequestCode.Companion.REQUEST_ATTACH_IMAGE
import ru.magzyumov.talan.utils.PreferenceHelper
import java.util.*
import javax.inject.Inject


class AddFragment : BaseFragment(R.layout.fragment_add), ImageDialog.Listener {
    override val binding by viewBinding(FragmentAddBinding::bind)
    override val viewModel by viewModels<AddViewModel> {viewModelProviderFactory}

    private var todo: Todo? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        binding.btnSave.setOnClickListener {
            saveTodo()
        }

        prepareDataForEditing()

        observerLiveData()
    }

    private fun observerLiveData() {
        viewModel.getToaster().observe(viewLifecycleOwner, {toast ->
            toast?.let {
                fragmentInteraction.showToast(it)
            }
        })
    }

    private fun saveTodo() {
        if (isValid()) {
            todo?.let {
                it.apply {
                    this.title = binding.etTitle.text.toString()
                    this.description = binding.etDescription.text.toString()
                }
                viewModel.save(it) { findNavController().popBackStack() }
            }
        } else {
            fragmentInteraction.showToast(getString(R.string.is_empty))
        }
    }

    private fun isValid(): Boolean {
        return !(binding.etTitle.text.isNullOrEmpty()
                && binding.etDescription.text.isNullOrEmpty())
    }

    private fun prepareDataForEditing() {
        arguments?.let {
            val safeArgs = AddFragmentArgs.fromBundle(it)
            safeArgs.todo?.let {parcel ->
                todo = parcel
                binding.etTitle.setText(parcel.title.toString())
                binding.etDescription.setText(parcel.description.toString())
                fragmentInteraction.changePageTitle(getString(R.string.title_edit))
            } ?: run {
                todo = Todo(date = Date())
                fragmentInteraction.changePageTitle(getString(R.string.title_add))
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.detailed_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.attachImage -> {
                val dialog: ImageDialog = ImageDialog.newInstance(this)
                dialog.show(requireActivity().supportFragmentManager, "attach_fragment")
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onChangeClick() {
        takePhoto()
    }

    override fun onRemoveClick() {
        todo?.apply {
            this.image = null
        }
        fragmentInteraction.showToast(getString(R.string.success))
    }

    private fun takePhoto() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
            .setType("image/*")
            .addCategory(Intent.CATEGORY_OPENABLE)
        startActivityForResult(intent, REQUEST_ATTACH_IMAGE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode != Activity.RESULT_OK) {
            return
        }

        when(requestCode){
            REQUEST_ATTACH_IMAGE -> {
                data?.let {
                    todo?.apply {
                        this.image = it.data.toString()
                        fragmentInteraction.showToast(getString(R.string.success))
                    }
                }
            }
        }
    }
}