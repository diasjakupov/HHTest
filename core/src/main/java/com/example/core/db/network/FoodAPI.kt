package com.example.core.db.network

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET


interface FoodAPI {

    @GET("058729bd-1402-4578-88de-265481fd7d54")
    suspend fun getAllCategories(): Response<FoodAPIResponse>


    @GET("aba7ecaa-0a70-453b-b62d-0e326c859b3b")
    suspend fun getAllMeals(): Response<MealsAPIResponse>
}