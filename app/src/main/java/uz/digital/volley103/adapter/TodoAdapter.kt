package uz.digital.volley103.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import uz.digital.volley103.databinding.TodoLayoutBinding
import uz.digital.volley103.model.Todo

class TodoAdapter : ListAdapter<Todo, TodoAdapter.TodoViewHolder>(DiffCallBack()) {
    lateinit var onClick: (id: Int) -> Unit

    private class DiffCallBack : DiffUtil.ItemCallback<Todo>() {
        override fun areItemsTheSame(oldItem: Todo, newItem: Todo): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Todo, newItem: Todo): Boolean {
            return oldItem == newItem
        }
    }

    inner class TodoViewHolder(private val binding: TodoLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(todo: Todo) {
            with(binding) {
                userId.text = todo.id.toString()
                title.text = todo.title
                completed.isChecked = todo.completed
            }
            itemView.setOnClickListener {
                onClick(todo.id)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        return TodoViewHolder(
            TodoLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}