package com.oguzhancakmak.recipeapp.data.local

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.oguzhancakmak.recipeapp.data.models.FavoriteFoodEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteViewModel(application: Application): AndroidViewModel(application) {

    val allFavoriteFoodDetail: LiveData<List<FavoriteFoodEntity>>
    private val repository: FavoriteRepository

    init{
        val favoriteDao = LocalDatabase.getAppDatabase(application)?.favoriteDao()
        repository = FavoriteRepository(favoriteDao!!)
        allFavoriteFoodDetail = repository.allFavoriteFoodDetail
    }

    fun insertFavoriteFoodDetail(favoriteFoodEntity: FavoriteFoodEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertFavoriteFoodDetail(favoriteFoodEntity)
        }
    }

    fun deleteFavoriteFoodDetail(favoriteFoodEntity: FavoriteFoodEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteFavoriteFoodDetail(favoriteFoodEntity)
        }
    }

}