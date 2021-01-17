package ru.magzyumov.talan.ui.fragment


import android.app.Application
import android.os.Bundle
import android.view.*
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.magzyumov.talan.R
import ru.magzyumov.talan.data.Todo
import ru.magzyumov.talan.databinding.FragmentListBinding
import ru.magzyumov.talan.ui.activity.AuthActivity
import ru.magzyumov.talan.ui.adapter.TodoAdapter
import ru.magzyumov.talan.ui.base.BaseFragment
import ru.magzyumov.talan.ui.viewmodel.ListViewModel
import ru.magzyumov.talan.utils.Constants.Preferences.Companion.USER_NAME
import ru.magzyumov.talan.utils.PreferenceHelper
import java.util.*
import javax.inject.Inject


class ListFragment : BaseFragment(R.layout.fragment_list), TodoAdapter.Interaction {
    override val binding by viewBinding(FragmentListBinding::bind)
    override val viewModel by viewModels<ListViewModel> {viewModelProviderFactory}

    private lateinit var todoAdapter: TodoAdapter
    private lateinit var passedTodoAdapter: TodoAdapter

    private var allTodo: List<Todo> = arrayListOf()
    private var passedTodo: List<Todo> = arrayListOf()

    @Inject
    lateinit var preferenceHelper: PreferenceHelper

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        fragmentInteraction.changePageTitle(getString(R.string.title_list))

        binding.fab.setOnClickListener {
            val navDirection = ListFragmentDirections.actionNavigationListToAddFragment()
            findNavController().navigate(navDirection)
        }

        initRecyclerView()
        observerLiveData()
        viewModel.refreshLists()
    }


    private fun initRecyclerView() {
        binding.recyclerViewTodo.apply {
            todoAdapter = TodoAdapter(
                    allTodo,
                    this@ListFragment
            )
            layoutManager = LinearLayoutManager(this@ListFragment.requireContext())
            adapter = todoAdapter
            val swipe = ItemTouchHelper(initSwipeToDelete(false))
            swipe.attachToRecyclerView(binding.recyclerViewTodo)
        }

        binding.recyclerViewPassed.apply {
            passedTodoAdapter = TodoAdapter(
                    passedTodo,
                    this@ListFragment
            )
            layoutManager = LinearLayoutManager(this@ListFragment.requireContext())
            adapter = passedTodoAdapter
            val swipe = ItemTouchHelper(initSwipeToDelete(true))
            swipe.attachToRecyclerView(binding.recyclerViewPassed)
        }
    }


    private fun observerLiveData() {
        viewModel.getTodoList().observe(viewLifecycleOwner, {todoList ->
            todoList?.let {
                allTodo = it
                todoAdapter.swap(it)
                binding.recyclerViewTodo.scrollToPosition(0)
            }
        })

        viewModel.getPassedTodoList().observe(viewLifecycleOwner, {todoList ->
            todoList?.let {
                passedTodo = it
                passedTodoAdapter.swap(it)
                binding.recyclerViewPassed.scrollToPosition(0)
            }
        })

        viewModel.getToaster().observe(viewLifecycleOwner, {toast ->
            toast?.let {
                fragmentInteraction.showToast(it)
            }
        })
    }

    override fun onItemSelected(item: Todo) {
        val navDirection = ListFragmentDirections.actionNavigationListToAddFragment(item)
        findNavController().navigate(navDirection)
    }

    override fun onPassSelected(passed: Boolean, item: Todo) {
        viewModel.update(item.apply { this.passed = passed; this.date = Date()})
    }

    private fun initSwipeToDelete(passed: Boolean): ItemTouchHelper.SimpleCallback {
        return object : ItemTouchHelper.SimpleCallback(
                0, ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                when(passed){
                    true -> {viewModel.delete(passedTodo[position])}
                    else -> {viewModel.delete(allTodo[position])}
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.logout -> {
                preferenceHelper.removeStringPreference(USER_NAME)
                fragmentInteraction.changeActivity(AuthActivity::class.java)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}