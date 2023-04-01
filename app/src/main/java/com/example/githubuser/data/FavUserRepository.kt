package com.example.githubuser.data

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.githubuser.database.FavUserDao
import com.example.githubuser.database.FavUserEntity
import com.example.githubuser.database.FavUserRoomDb
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavUserRepository (application: Application) {
    private val mFavUserDao : FavUserDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavUserRoomDb.getDatabase(application)
        mFavUserDao = db.favUserDao()
    }

    fun getAllFavorites(): LiveData<List<FavUserEntity>> = mFavUserDao.getAllFavorite()

    fun insert(favUserEntity: FavUserEntity) {
        executorService.execute { mFavUserDao.insert(favUserEntity) }
    }

    fun delete(id: Int) {
        executorService.execute { mFavUserDao.removeFavorite(id) }
    }
}