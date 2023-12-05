package com.example.githubuser.data

import android.app.Application
import com.example.githubuser.database.FavUserDao
import com.example.githubuser.database.FavUserEntity
import com.example.githubuser.database.FavUserRoomDb
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavUserRepository(application: Application) {

    private val mFavUserDao: FavUserDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavUserRoomDb.getDatabase(application)
        mFavUserDao = db.favUserDao()
    }

    fun insert(favUserEntity: FavUserEntity) {
        executorService.execute { mFavUserDao.insertFavorite(favUserEntity) }
    }

    fun delete(favUserEntity: FavUserEntity) {
        executorService.execute { mFavUserDao.delete(favUserEntity) }
    }

    fun getAllFavoriteUser() = mFavUserDao.getAllFavoriteUser()

    fun isFavorite(userId: String) = mFavUserDao.isFavorite(userId)
}