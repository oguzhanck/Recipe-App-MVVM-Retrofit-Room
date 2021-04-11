package com.oguzhancakmak.recipeapp.data.services

import com.oguzhancakmak.recipeapp.data.models.Category
import com.oguzhancakmak.recipeapp.data.models.Country
import com.oguzhancakmak.recipeapp.data.models.FoodDetail
import com.oguzhancakmak.recipeapp.data.models.FoodList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface RetrofitApiInterface {

    @GET("categories.php")
    fun getCategoryData(): Call<Category>

    @GET("list.php?a=list")
    fun getCountryData(): Call<Country>

    @GET("filter.php")
    fun getFoodListData(
        @Query("a") a: String? = null,
        @Query("c") c: String? = null
    ): Call<FoodList>

    @GET("lookup.php")
    fun getFoodDetailData(@Query("i") i: String): Call<FoodDetail>

}