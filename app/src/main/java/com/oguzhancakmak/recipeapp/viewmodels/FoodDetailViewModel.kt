package com.oguzhancakmak.recipeapp.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.oguzhancakmak.recipeapp.data.models.FoodDetail
import com.oguzhancakmak.recipeapp.data.services.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FoodDetailViewModel : ViewModel() {
    private val _foodDetail = MutableLiveData<FoodDetail>()
    private val foodDetail: LiveData<FoodDetail>

    init {
        foodDetail = _foodDetail
    }

    fun fetchNewFoodDetailData(foodID: String): LiveData<FoodDetail> {
        ApiClient.build().getFoodDetailData(foodID).enqueue(object : Callback<FoodDetail> {

            override fun onFailure(call: Call<FoodDetail>, t: Throwable) {
                Log.e("FoodDetail View Model", t.toString())
            }

            override fun onResponse(
                call: Call<FoodDetail>,
                response: Response<FoodDetail>
            ) {
                _foodDetail.value = response.body()
            }
        })

        return foodDetail
    }
}