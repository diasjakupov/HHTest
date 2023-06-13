package com.example.core.db.network

sealed class FoodResult<T> {
    class Success<T>(val data: T?): FoodResult<T>()
    class Error<T>(val message: String): FoodResult<T>()
    class Loading<T>: FoodResult<T>()
}