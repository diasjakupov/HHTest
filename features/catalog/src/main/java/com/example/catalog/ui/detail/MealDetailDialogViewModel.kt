package com.example.catalog.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.catalog.ui.meals.MealsViewModel
import com.example.core.repository.Repository

class MealDetailDialogViewModel(repository: Repository) : ViewModel() {
    // TODO: Implement the ViewModel
}

class MealsDetailViewModelFactory(
    private val repository: Repository
): ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(
        modelClass: Class<T>,
        extras: CreationExtras
    ): T {
        return MealDetailDialogViewModel(
            repository
        ) as T
    }
}