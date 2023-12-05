package com.example.githubuser.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FavUserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertFavorite(favUserEntity: FavUserEntity)

    @Delete
    fun delete(favUserEntity: FavUserEntity)

    @Query("SELECT * from favorite_user")
    fun getAllFavoriteUser(): LiveData<List<FavUserEntity>>

    @Query("SELECT EXISTS(SELECT * FROM favorite_user WHERE login = :login)")
    fun isFavorite(login: String): LiveData<Boolean>

}