package com.example.core.repository

import android.icu.util.Calendar
import android.util.Log
import com.example.core.datasource.LocalDataSource
import com.example.core.datasource.RemoteDataSource
import com.example.core.db.network.FoodAPIResponse
import com.example.core.db.network.FoodResult
import com.example.core.db.network.MealsAPIResponse
import com.example.core.models.MealEntry
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

class Repository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) {

    fun getAllCategories(): Flow<FoodResult<FoodAPIResponse>> {
        return flow<FoodResult<FoodAPIResponse>> {
            emit(remoteDataSource.getAllCategories())
        }
    }

    fun getAllMeals(): Flow<FoodResult<MealsAPIResponse>> {
        return flow<FoodResult<MealsAPIResponse>> {
            emit(remoteDataSource.getAllMeals())
        }
    }

    fun addEntryToShoppingCart(mealEntry: MealEntry) {
        localDataSource.addNewEntry(mealEntry)
    }


    fun increaseAmount(mealEntry: MealEntry):MutableSet<Pair<MealEntry, Int>> {
        return localDataSource.increaseAmountOfShoppingCartItem(mealEntry)
    }

    fun decreaseAmount(mealEntry: MealEntry):MutableSet<Pair<MealEntry, Int>> {
        return localDataSource.decreaseAmountOfShoppingCartItem(mealEntry)
    }

    fun getShoppingCart(): MutableSet<Pair<MealEntry, Int>> {
        Log.e("TAG REPO", "${localDataSource.getShoppingCart()} $localDataSource")
        return localDataSource.getShoppingCart()
    }

    fun getCurrentTime():String{
        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
        return dateFormat.format(calendar.time)
    }
}