package com.oguzhancakmak.recipeapp.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.oguzhancakmak.recipeapp.data.models.Country
import com.oguzhancakmak.recipeapp.data.services.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CountryViewModel : ViewModel() {
    private val _country = MutableLiveData<Country>()
    private val country: LiveData<Country>

    init {
        country = _country
    }

    fun fetchNewCountryData(): LiveData<Country> {
        ApiClient.build().getCountryData().enqueue(object : Callback<Country> {

            override fun onFailure(call: Call<Country>, t: Throwable) {
                Log.e("Country View Model", t.toString())
            }

            override fun onResponse(
                call: Call<Country>,
                response: Response<Country>
            ) {
                _country.value = response.body()
            }
        })

        return country
    }

}