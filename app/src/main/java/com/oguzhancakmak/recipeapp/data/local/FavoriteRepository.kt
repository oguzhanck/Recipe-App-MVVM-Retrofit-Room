package com.oguzhancakmak.recipeapp.data.local

import androidx.lifecycle.LiveData
import com.oguzhancakmak.recipeapp.data.models.FavoriteFoodEntity

class FavoriteRepository(private val favoriteDao: FavoriteDao) {

    val allFavoriteFoodDetail:LiveData<List<FavoriteFoodEntity>> = favoriteDao.findAll()

    fun insertFavoriteFoodDetail(favoriteFoodEntity: FavoriteFoodEntity) {
        favoriteDao.insert(favoriteFoodEntity)
    }

    fun deleteFavoriteFoodDetail(favoriteFoodEntity: FavoriteFoodEntity) {
        favoriteDao.delete(favoriteFoodEntity)
    }
}