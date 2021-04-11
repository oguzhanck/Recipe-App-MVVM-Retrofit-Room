package com.oguzhancakmak.recipeapp.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.oguzhancakmak.recipeapp.data.models.FavoriteFoodEntity


@Dao
interface FavoriteDao {
    @Query("SELECT * FROM FoodDetails ORDER BY strMeal ASC")
    fun findAll(): LiveData<List<FavoriteFoodEntity>>

    @Delete
    fun delete(favoriteFoodEntity: FavoriteFoodEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(favoriteFoodEntity: FavoriteFoodEntity)
}