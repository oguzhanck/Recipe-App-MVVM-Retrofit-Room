package com.oguzhancakmak.recipeapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.oguzhancakmak.recipeapp.data.models.FavoriteFoodEntity

const val DB_VERSION = 1

@Database(entities = [FavoriteFoodEntity::class], version = DB_VERSION, exportSchema = true)
abstract class LocalDatabase : RoomDatabase() {

    abstract fun favoriteDao(): FavoriteDao

    companion object {
        private var INSTANCE: LocalDatabase? = null
        fun getAppDatabase(context: Context): LocalDatabase? {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder<LocalDatabase>(
                    context.applicationContext, LocalDatabase::class.java, "FoodDetailDB"
                )
                    .allowMainThreadQueries()
                    .build()
            }

            return INSTANCE
        }
    }
}