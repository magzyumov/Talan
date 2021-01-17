package ru.magzyumov.talan.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ru.magzyumov.talan.R
import ru.magzyumov.talan.databinding.DialogImageBinding

class ImageDialog: BottomSheetDialogFragment(), View.OnClickListener {

    companion object {
        fun newInstance(listener: Listener): ImageDialog {
            val dialog = ImageDialog()
            dialog.setListener(listener)
            return dialog
        }
    }

    private var _binding: DialogImageBinding? = null
    private val binding get() = _binding!!

    private var listener: Listener? = null

    interface Listener {
        fun onChangeClick()
        fun onRemoveClick()
    }

    fun setListener(listener: Listener) {
        this.listener = listener
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        _binding = DialogImageBinding.inflate(inflater, container, false)

        binding.btnCancel.setOnClickListener(this)
        binding.btnChange.setOnClickListener(this)
        binding.btnRemove.setOnClickListener(this)

        return binding.root
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.btnChange -> listener?.onChangeClick()
            R.id.btnRemove -> listener?.onRemoveClick()
            R.id.btnCancel -> dismiss()
        }
        dismiss()
    }
}