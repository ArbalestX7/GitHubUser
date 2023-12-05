package com.example.githubuser.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.githubuser.data.FavUserRepository
import com.example.githubuser.database.FavUserEntity

class FavoriteViewModel(application: Application) : ViewModel() {
    private val mFavUserRepository: FavUserRepository = FavUserRepository(application)

    fun insert(favUserEntity: FavUserEntity) {
        mFavUserRepository.insert(favUserEntity)
    }

    fun delete(favUserEntity: FavUserEntity) {
        mFavUserRepository.delete(favUserEntity)
    }

    fun isFavorite(userId: String): LiveData<Boolean> =
        mFavUserRepository.isFavorite(userId)

    fun getAllFavoriteUser(): LiveData<List<FavUserEntity>> =
        mFavUserRepository.getAllFavoriteUser()
}
