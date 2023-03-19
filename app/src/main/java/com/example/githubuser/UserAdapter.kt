package com.example.githubuser

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class UserAdapter(private val listUser: List<ItemsItem>)  : RecyclerView.Adapter<UserAdapter.ViewHolder>(){


    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.item_user, viewGroup, false))


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val Users = listUser[position]
        holder.tvName.text = Users.login
        Glide.with(holder.itemView.context)
            .load(Users.avatarUrl)
            .circleCrop()
            .into(holder.imgPhoto)

        holder.itemView.setOnClickListener {
            val intentDetail = Intent(holder.itemView.context, DetailUserActivity::class.java)
            intentDetail.putExtra(DetailUserActivity.EXTRA_USER, Users.login)
            holder.itemView.context.startActivity(intentDetail)
        }
    }

    override fun getItemCount() = listUser.size




    class ViewHolder (view : View) : RecyclerView.ViewHolder(view){
    val tvName : TextView = view.findViewById(R.id.tv_username)
    val imgPhoto : ImageView = view.findViewById(R.id.img_user_photo)
    }
}
