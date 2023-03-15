package com.example.githubuser

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.githubuser.databinding.ItemUserBinding

class UserAdapter  : RecyclerView.Adapter<UserAdapter.ViewHolder>(){
    private val listUser= ArrayList<ItemsItem>()
    private var onItemClickCallback: OnItemClickCallback?=null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setList(items: List<ItemsItem>) {
        listUser.clear()
        listUser.addAll(items)
        notifyDataSetChanged()
    }

    class ViewHolder (val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root){
        fun userData (item: ItemsItem){
            binding.tvUsername.text = item.login
            Glide.with(itemView)
                .load(item.avatarUrl)
                .circleCrop()
                .into(binding.imgUserPhoto)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder{
        val view = ItemUserBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       holder.userData(listUser[position])
        holder.itemView.setOnClickListener{onItemClickCallback?.onItemClicked(listUser[position])}
    }

    override fun getItemCount() = listUser.size
    interface OnItemClickCallback {
        fun onItemClicked(data: ItemsItem)
    }
}