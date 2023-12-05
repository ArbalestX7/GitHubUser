package com.example.githubuser.util

import androidx.recyclerview.widget.DiffUtil
import com.example.githubuser.data.database.FavUserEntity

class UserDiffCallback(
    private val mOldFavUserList: List<FavUserEntity>,
    private val mNewFavUserList: List<FavUserEntity>,
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = mOldFavUserList.size

    override fun getNewListSize(): Int = mNewFavUserList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return mOldFavUserList[oldItemPosition].login == mNewFavUserList[newItemPosition].login
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = mOldFavUserList[oldItemPosition]
        val newItem = mNewFavUserList[newItemPosition]
        return oldItem.login == newItem.login && oldItem.avatar_url == newItem.avatar_url
    }

}