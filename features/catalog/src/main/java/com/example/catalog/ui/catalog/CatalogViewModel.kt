package com.example.catalog.ui.catalog

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.core.repository.Repository
import com.example.core.db.network.FoodAPIResponse
import com.example.core.db.network.FoodResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class CatalogViewModel(
    private val repository: Repository,
) : ViewModel() {
    private var _categoryList = MutableStateFlow<FoodResult<FoodAPIResponse>>(FoodResult.Loading())
    val categoryList = _categoryList


    fun getAllCategories(){
        viewModelScope.launch(Dispatchers.IO) {
            Log.e("TAG", "call")
            repository.getAllCategories().collectLatest {
                _categoryList.emit(it)
            }
        }
    }

    fun getCurrentTime() = repository.getCurrentTime()
}


class CatalogViewModelFactory(
    private val repository: Repository
): ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(
        modelClass: Class<T>,
        extras: CreationExtras
    ): T {
        return CatalogViewModel(
            repository
        ) as T
    }
}

