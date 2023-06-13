package com.example.core.db.network

import com.example.core.models.MealEntry

data class MealsAPIResponse (
    val dishes: List<MealEntry>
)
