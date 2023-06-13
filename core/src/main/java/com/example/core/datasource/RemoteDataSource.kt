package com.example.core.datasource

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import com.example.core.db.network.FoodAPI
import com.example.core.db.network.FoodAPIResponse
import com.example.core.db.network.FoodResult
import com.example.core.db.network.MealsAPIResponse
import com.example.core.models.Category
import com.example.core.models.MealEntry


class RemoteDataSource(
    private val foodAPI: FoodAPI,
    private val mContext: Context
) {
    private fun isConnected(): Boolean {
        val connectivityManager =
            mContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val networkCapabilities = connectivityManager.activeNetwork ?: return false
        val activeNetwork =
            connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false

        return when {
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }

    suspend fun getAllCategories(): FoodResult<FoodAPIResponse> {
        return if (isConnected()) {
//            return FoodResult.Success(
//                data = FoodAPIResponse(
//                    arrayListOf(
//                        Category(
//                            1,
//                            image_url = "",
//                            name = "Test"
//                        )
//                    )
//                )
//            )
            val response = foodAPI.getAllCategories()
            if (response.isSuccessful && isConnected()) {
                FoodResult.Success(data = response.body())
            } else {
                FoodResult.Error(
                    message = response.message()
                )
            }
        } else {
            FoodResult.Error(
                message = "No Internet connection"
            )
        }

//        return FoodResult.Loading()
    }

    suspend fun getAllMeals(): FoodResult<MealsAPIResponse> {
//        return FoodResult.Success(
//            data = MealsAPIResponse(
//                arrayListOf(
//                    MealEntry(
//                        description = "kjdfhakjf",
//                        id = 1,
//                        image_url = "",
//                        name = "Test1",
//                        price = 99,
//                        tegs = listOf("1", "2"),
//                        weight = 100
//                    ),MealEntry(
//                        description = "kjdfhakjf2",
//                        id = 2,
//                        image_url = "",
//                        name = "Test2",
//                        price = 10,
//                        tegs = listOf("2", "3"),
//                        weight = 100
//                    )
//                )
//            )
//        )

        val response = foodAPI.getAllMeals()
        return if (response.isSuccessful) {
            FoodResult.Success(data = response.body())
        } else {
            FoodResult.Error(
                message = response.message()
            )
        }
    }
}