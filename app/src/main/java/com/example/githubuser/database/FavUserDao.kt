package com.example.githubuser.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FavUserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(favUserEntity: FavUserEntity)

    @Query("DELETE FROM FavUserEntity WHERE FavUserEntity.id = :id")
    fun removeFavorite(id: Int)

    @Query("SELECT * FROM FavUserEntity ORDER by login ASC")
    fun getAllFavorite(): LiveData<List<FavUserEntity>>

    @Query("SELECT * FROM FavUserEntity WHERE FavUserEntity.id = :id")
    fun getFavUserById (id: Int): LiveData<FavUserEntity>
}