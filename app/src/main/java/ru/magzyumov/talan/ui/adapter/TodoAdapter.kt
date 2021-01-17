package ru.magzyumov.talan.ui.adapter

import android.app.Application
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.magzyumov.talan.R
import ru.magzyumov.talan.data.Todo
import ru.magzyumov.talan.databinding.ItemTodoBinding
import ru.magzyumov.talan.utils.formatToString


class TodoAdapter(
        private val todoList: List<Todo>,
        private val interaction: Interaction? = null
) : RecyclerView.Adapter<TodoAdapter.ViewHolder>() {

    private val todos = mutableListOf<Todo>()

    init {
        todos.addAll(todoList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_todo, parent, false)
        return ViewHolder(view, interaction)
    }

    override fun getItemCount() = todos.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(item = todos[position])
    }

    fun swap(todo: List<Todo>) {
        val diffCallback = DiffCallback(this.todos, todo)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        this.todos.clear()
        this.todos.addAll(todo)
        diffResult.dispatchUpdatesTo(this)
    }


    inner class ViewHolder(
        itemView: View,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemTodoBinding.bind(itemView)

        fun bind(item: Todo) {
            loadImage(binding.ivImage, item.image)

            binding.cbPassed.isChecked = item.passed
            binding.tvTitle.text = item.title
            binding.tvDate.text = item.date.formatToString()

            binding.cbPassed.setOnClickListener {
                interaction?.onPassSelected((it as CheckBox).isChecked, item)
            }

            itemView.setOnClickListener {
                interaction?.onItemSelected(item)
            }
        }
    }

    private fun loadImage(view: ImageView, image: String?) {
        image?.let {
            Glide.with(view.context)
                    .load(Uri.parse(image))
                    .centerCrop()
                    .fitCenter()
                    .into(view)
        }
    }

    interface Interaction {
        fun onItemSelected(item: Todo)
        fun onPassSelected(passed: Boolean, item: Todo)
    }

    class DiffCallback(
            private val oldList: List<Todo>,
            private val newList: List<Todo>
    ) : DiffUtil.Callback() {

        override fun getOldListSize() = oldList.size

        override fun getNewListSize() = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id == newList[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id == newList[newItemPosition].id
                    && oldList[oldItemPosition].title == newList[newItemPosition].title
                    && oldList[oldItemPosition].description == newList[newItemPosition].description
                    && oldList[oldItemPosition].image == newList[newItemPosition].image
                    && oldList[oldItemPosition].date == newList[newItemPosition].date
        }
    }
}