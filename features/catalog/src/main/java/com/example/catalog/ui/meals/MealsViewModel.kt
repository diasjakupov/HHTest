package com.example.catalog.ui.meals

import android.util.Log
import androidx.core.view.get
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.catalog.ui.catalog.CatalogViewModel
import com.example.core.db.network.FoodAPIResponse
import com.example.core.db.network.FoodResult
import com.example.core.db.network.MealsAPIResponse
import com.example.core.models.MealEntry
import com.example.core.repository.Repository
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

class MealsViewModel (
    private val repository: Repository
        ): ViewModel() {

    private var _initialList = MutableStateFlow<List<MealEntry>>(emptyList())
    private var _mealsList = MutableStateFlow<FoodResult<MealsAPIResponse>>(FoodResult.Loading())
    val mealsList = _mealsList

    private var _filterValues = MutableStateFlow(mutableSetOf<String>())
    val filterValues = _filterValues

    fun filterMealsList(text: String){
        viewModelScope.launch {
            if (_mealsList.value is FoodResult.Success) {
                val initialList = _initialList.value
                val filteredValues = initialList.filter {
                    it.tegs.contains(text)
                }
                _mealsList.emit(FoodResult.Success(data = MealsAPIResponse(dishes = filteredValues)))
            }

        }
    }


    fun addToCart(meal: MealEntry){
        viewModelScope.launch{
            Log.e("TAG MEAL", repository.toString())
            repository.addEntryToShoppingCart(meal)
        }
    }


    fun getAllMeals(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllMeals().collectLatest {result->
                _mealsList.emit(result)

                if (result is FoodResult.Success) {
                    val dishes = result.data?.dishes ?: emptyList()

                    val tagsToAdd = mutableSetOf<String>()
                    dishes.forEach { mealEntry ->
                        tagsToAdd.addAll(mealEntry.tegs)
                    }
                    _filterValues.emit((_filterValues.value + tagsToAdd).toMutableSet())

                    _initialList.emit(dishes)
                }
            }
        }
    }


}



class MealsViewModelFactory(
    private val repository: Repository
): ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(
        modelClass: Class<T>,
        extras: CreationExtras
    ): T {
        return MealsViewModel(
            repository
        ) as T
    }
}