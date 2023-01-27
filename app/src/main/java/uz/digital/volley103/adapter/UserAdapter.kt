package uz.digital.volley103.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import uz.digital.volley103.databinding.UserLayoutBinding
import uz.digital.volley103.model.User


class UserAdapter : ListAdapter<User, UserAdapter.UserViewHolder>(DiffCallBack()) {
    lateinit var onClick: (id: Int) -> Unit

    private class DiffCallBack : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem == newItem
        }
    }

    inner class UserViewHolder(private val binding: UserLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            with(binding) {
                Glide.with(imageView)
                    .load(user.image)
                    .circleCrop()
                    .into(imageView)
                login.text = user.login
            }
            itemView.setOnClickListener {
                onClick(user.id)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(
            UserLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}