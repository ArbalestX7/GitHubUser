package com.example.githubuser

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class UserAdapter(private val listUser: ArrayList<String>)  : RecyclerView.Adapter<UserAdapter.ViewHolder>(){

    private var onItemClickCallback: OnItemClickCallback?=null
    interface OnItemClickCallback {
        fun onItemClicked(data: List<String>)
    }
    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.item_user, viewGroup, false))


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val Users = listUser[position].split(";")
        holder.tvName.text = Users[1]
        Glide.with(holder.itemView.context)
            .load(Users[0])
            .circleCrop()
            .into(holder.imgPhoto)
        holder.itemView.setOnClickListener{onItemClickCallback?.onItemClicked(Users)}
    }

    override fun getItemCount() = listUser.size




    class ViewHolder (view : View) : RecyclerView.ViewHolder(view){
    val tvName : TextView = view.findViewById(R.id.tv_username)
    val imgPhoto : ImageView = view.findViewById(R.id.img_user_photo)
    }
}
