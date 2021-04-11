package com.oguzhancakmak.recipeapp.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


data class CategoryEntity(
    val idCategory: String,
    val strCategory: String,
    val strCategoryDescription: String,
    val strCategoryThumb: String
)

data class CountryEntity(
    val strArea: String
)

data class FoodListEntity(
    val idMeal: String,
    val strMeal: String,
    val strMealThumb: String
)

data class FoodDetailEntity(
    val idMeal: Int,
    val strMeal: String,
    val strDrinkAlternate: String,
    val strCategory: String,
    val strArea: String,
    val strInstructions: String,
    val strMealThumb: String,
    val strTags: String,
    val strYoutube: String,
    val strIngredient1: String,
    val strIngredient2: String,
    val strIngredient3: String,
    val strIngredient4: String,
    val strIngredient5: String,
    val strIngredient6: String,
    val strIngredient7: String,
    val strIngredient8: String,
    val strIngredient9: String,
    val strIngredient10: String,
    val strIngredient11: String,
    val strIngredient12: String,
    val strIngredient13: String,
    val strIngredient14: String,
    val strIngredient15: String,
    val strIngredient16: String,
    val strIngredient17: String,
    val strIngredient18: String,
    val strIngredient19: String,
    val strIngredient20: String?,
    val strMeasure1: String,
    val strMeasure2: String,
    val strMeasure3: String,
    val strMeasure4: String,
    val strMeasure5: String,
    val strMeasure6: String,
    val strMeasure7: String,
    val strMeasure8: String,
    val strMeasure9: String,
    val strMeasure10: String,
    val strMeasure11: String,
    val strMeasure12: String,
    val strMeasure13: String,
    val strMeasure14: String,
    val strMeasure15: String,
    val strMeasure16: String,
    val strMeasure17: String,
    val strMeasure18: String,
    val strMeasure19: String,
    val strMeasure20: String,
    val strSource: String,
    val strImageSource: String,
    val strCreativeCommonsConfirmed: String,
    val dateModified: String,
)

@Entity(tableName = "FoodDetails")
data class FavoriteFoodEntity(
    @PrimaryKey @ColumnInfo(name = "mealID") val mealID: String,
    @ColumnInfo(name = "strMeal") val strMeal: String,
    @ColumnInfo(name = "strMealThumb") val strMealThumb: String,
)
