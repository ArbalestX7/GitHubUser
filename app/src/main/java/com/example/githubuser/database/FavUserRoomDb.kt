package com.example.githubuser.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [FavUserEntity::class], version = 1)
abstract class FavUserRoomDb : RoomDatabase() {

    abstract fun favUserDao(): FavUserDao

    companion object {
        @Volatile
        private var INSTANCE : FavUserRoomDb? = null

        @JvmStatic
        fun getDatabase(context: Context): FavUserRoomDb {
            if (INSTANCE == null){
                synchronized(FavUserRoomDb::class.java){
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                    FavUserRoomDb::class.java, "favUser_database")
                    .build()
                }
            }
            return INSTANCE as FavUserRoomDb
        }
    }
}