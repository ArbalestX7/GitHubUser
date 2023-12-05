package com.example.githubuser.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuser.R
import com.example.githubuser.database.FavUserEntity
import com.example.githubuser.databinding.ItemUserBinding
import com.example.githubuser.util.UserDiffCallback

class FavoriteUserAdapter(
    private val listener: OnUserFavoriteClick? = null,
) : RecyclerView.Adapter<FavoriteUserAdapter.ViewHolder>() {

    private val mListFavUser = ArrayList<FavUserEntity>()

    interface OnUserFavoriteClick {
        fun onUserFavoriteClick(username: String)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemUserBinding.bind(itemView)

        fun bindItem(item: FavUserEntity) {
            with(binding) {
                tvUsername.text = item.login
                Glide.with(itemView)
                    .load(item.avatar_url)
                    .circleCrop()
                    .into(imgUserPhoto)

                root.setOnClickListener {
                    if (item.login != null)
                        listener?.onUserFavoriteClick(item.login)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(mListFavUser[position])
    }

    override fun getItemCount(): Int = mListFavUser.size

    fun setListFavorite(listUser: List<FavUserEntity>) {
        val diffCallback = UserDiffCallback(mListFavUser, listUser)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        mListFavUser.clear()
        mListFavUser.addAll(listUser)
        diffResult.dispatchUpdatesTo(this)
    }
}