package com.oguzhancakmak.recipeapp.data.services

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://www.themealdb.com/api/json/v1/1/"

object ApiClient {

    private var retrofitApiInterface:RetrofitApiInterface? = null

    fun build(): RetrofitApiInterface {
        val builder: Retrofit.Builder = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())

        val httpClient: OkHttpClient.Builder = OkHttpClient.Builder()

        val retrofit: Retrofit = builder.client(httpClient.build()).build()
        retrofitApiInterface = retrofit.create(
            RetrofitApiInterface::class.java
        )

        return retrofitApiInterface as RetrofitApiInterface
    }

}

