package com.oguzhancakmak.recipeapp.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.oguzhancakmak.recipeapp.data.models.Category
import com.oguzhancakmak.recipeapp.data.services.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryViewModel : ViewModel() {

    private val _category = MutableLiveData<Category>()
    private val category: LiveData<Category>

    init {
        category = _category
    }

    fun fetchNewCategoryData(): LiveData<Category> {
        ApiClient.build().getCategoryData().enqueue(object : Callback<Category> {

            override fun onFailure(call: Call<Category>, t: Throwable) {
                Log.e("Category View Model", t.toString())
            }

            override fun onResponse(
                call: Call<Category>,
                response: Response<Category>
            ) {
                _category.value = response.body()
            }
        })

        return category
    }

}