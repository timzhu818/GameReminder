package com.example.gamereminder.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitController {

    const val BASE_URL = "https://www.thesportsdb.com"
    val retrofit = Retrofit.Builder().baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create()).build()

    val requestApi: RequestApi = retrofit.create(RequestApi::class.java)
}

