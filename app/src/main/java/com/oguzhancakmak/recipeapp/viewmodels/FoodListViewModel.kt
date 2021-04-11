package com.oguzhancakmak.recipeapp.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.oguzhancakmak.recipeapp.data.models.FoodList
import com.oguzhancakmak.recipeapp.data.services.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FoodListViewModel : ViewModel() {

    private val _foodList = MutableLiveData<FoodList>()
    private val foodList: LiveData<FoodList>

    init {
        foodList = _foodList
    }

    fun fetchNewFoodListData(path1: String? = null, path2: String? = null): LiveData<FoodList> {
        ApiClient.build().getFoodListData(a = path1, c = path2)
            .enqueue(object : Callback<FoodList> {

                override fun onFailure(call: Call<FoodList>, t: Throwable) {
                    Log.e("FoodList View Model", t.toString())
                }

                override fun onResponse(
                    call: Call<FoodList>,
                    response: Response<FoodList>
                ) {
                    _foodList.value = response.body()
                }
            })

        return foodList
    }
}