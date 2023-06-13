package com.example.core.datasource

import android.content.Context
import com.example.core.models.MealEntry
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import android.content.SharedPreferences
import android.util.Log

class ShoppingCartPreferences(context: Context) {
    init {
        Log.e("TAG PREF", "$context")
    }

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("shopping_cart_preferences", Context.MODE_PRIVATE)

    fun getAllShoppingCartItems(): MutableSet<Pair<MealEntry, Int>> {
        val serializedItems = sharedPreferences.getStringSet("shopping_cart_items", null)
        val items = mutableSetOf<Pair<MealEntry, Int>>()

        serializedItems?.forEach { serializedPair ->
            val pair = deserializePair(serializedPair)
            pair?.let { items.add(it) }
        }

        return items
    }

    fun deleteAllShoppingCartItems() {
        sharedPreferences.edit().remove("shopping_cart_items").apply()
    }

    fun increaseAmount(mealId: Int): MutableSet<Pair<MealEntry, Int>> {
        val serializedItems = sharedPreferences.getStringSet("shopping_cart_items", null)
        val updatedItems = mutableSetOf<String>()

        serializedItems?.forEach { serializedPair ->
            val pair = deserializePair(serializedPair)
            if (pair?.first?.id == mealId) {
                val updatedAmount = pair.second + 1
                updatedItems.add(serializePair(pair.first, updatedAmount))
            } else {
                updatedItems.add(serializedPair)
            }
        }

        sharedPreferences.edit().putStringSet("shopping_cart_items", updatedItems).apply()
        return getAllShoppingCartItems()
    }

    fun decreaseAmount(mealId: Int): MutableSet<Pair<MealEntry, Int>> {
        val serializedItems = sharedPreferences.getStringSet("shopping_cart_items", null)
        val updatedItems = mutableSetOf<String>()

        serializedItems?.forEach { serializedPair ->
            val pair = deserializePair(serializedPair)
            if (pair?.first?.id == mealId) {
                val updatedAmount = pair.second - 1
                if (updatedAmount > 0) {
                    updatedItems.add(serializePair(pair.first, updatedAmount))
                }
            } else {
                updatedItems.add(serializedPair)
            }
        }

        sharedPreferences.edit().putStringSet("shopping_cart_items", updatedItems).apply()
        return getAllShoppingCartItems()
    }

    fun addNewEntry(entry: MealEntry) {
        val serializedItems = sharedPreferences.getStringSet("shopping_cart_items", null)
        val updatedItems = mutableSetOf<String>()

        serializedItems?.let { updatedItems.addAll(it) }

        // Check if the entry already exists in the shopping cart
        val existingPair = updatedItems.firstOrNull {
            val pair = deserializePair(it)
            pair?.first?.id == entry.id
        }

        if (existingPair != null) {
            // If the entry already exists, increase its amount
            val pair = deserializePair(existingPair)
            pair?.let {
                val updatedAmount = it.second + 1
                updatedItems.remove(existingPair)
                updatedItems.add(serializePair(it.first, updatedAmount))
            }
        } else {
            // If the entry does not exist, add a new pair
            updatedItems.add(serializePair(entry, 1))
        }

        sharedPreferences.edit().putStringSet("shopping_cart_items", updatedItems).apply()
    }

    companion object {
        private fun deserializePair(serializedPair: String): Pair<MealEntry, Int>? {
            val parts = serializedPair.split("|")
            if (parts.size != 8) return null

            val mealId = parts[0].toIntOrNull() ?: return null
            val name = parts[1]
            val description = parts[2]
            val price = parts[3].toIntOrNull() ?: return null
            val weight = parts[4].toIntOrNull() ?: return null
            val imageUrl = parts[5]
            val tags = parts[6].split(",") // Assuming tags are separated by commas
            val amount = parts[7].toIntOrNull() ?: return null

            val mealEntry = MealEntry(
                id = mealId,
                name = name,
                description = description,
                price = price,
                weight = weight,
                image_url = imageUrl,
                tegs = tags,
            )
            return mealEntry to amount
        }
        private fun serializePair(mealEntry: MealEntry, amount: Int): String {
            val tagsString = mealEntry.tegs.joinToString(",")
            return "${mealEntry.id}|${mealEntry.name}|${mealEntry.description}|${mealEntry.price}|${mealEntry.weight}|${mealEntry.image_url}|$tagsString|$amount"
        }
    }

}

