package com.example.core.datasource

import android.util.Log
import com.example.core.models.MealEntry
import kotlinx.coroutines.flow.MutableStateFlow

class LocalDataSource(
    private val shoppingCartPreferences: ShoppingCartPreferences
){
    fun getShoppingCart(): MutableSet<Pair<MealEntry, Int>>{
        return shoppingCartPreferences.getAllShoppingCartItems()
    }

    fun deleteShoppingCart(){
        shoppingCartPreferences.deleteAllShoppingCartItems()
    }

    fun increaseAmountOfShoppingCartItem(entry: MealEntry):MutableSet<Pair<MealEntry, Int>>{
        return shoppingCartPreferences.increaseAmount(entry.id)
    }

    fun decreaseAmountOfShoppingCartItem(entry: MealEntry): MutableSet<Pair<MealEntry, Int>>{
        return shoppingCartPreferences.decreaseAmount(entry.id)
    }

    fun addNewEntry(entry: MealEntry){
        shoppingCartPreferences.addNewEntry(entry)
    }
}