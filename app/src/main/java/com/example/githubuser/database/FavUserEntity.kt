package com.example.githubuser.database

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "favorite_user")
@Parcelize
data class FavUserEntity(
    @PrimaryKey(autoGenerate = false)
    var login: String = "",
    var avatar_url: String? = null,
) : Parcelable