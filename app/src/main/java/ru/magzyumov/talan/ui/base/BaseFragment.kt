package ru.magzyumov.talan.ui.base

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import dagger.android.support.DaggerFragment
import ru.magzyumov.talan.utils.FragmentViewBindingDelegate
import ru.magzyumov.talan.utils.ViewModelProviderFactory
import javax.inject.Inject


abstract class BaseFragment(@LayoutRes val contentLayoutId: Int): DaggerFragment() {
    private val TAG = javaClass.simpleName

    abstract val binding: ViewBinding
    abstract val viewModel: ViewModel

    protected lateinit var fragmentInteraction: BaseFragmentInteraction

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BaseFragmentInteraction) fragmentInteraction = context
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(contentLayoutId, container, false)
    }

    protected fun debugLog(message: String){
        Log.e(TAG, message)
    }

    protected fun <T: ViewBinding> Fragment.viewBinding(viewBindingFactory: (View) -> T) =
        FragmentViewBindingDelegate(this, viewBindingFactory)
}