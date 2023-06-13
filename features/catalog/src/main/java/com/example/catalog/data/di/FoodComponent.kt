package com.example.catalog.data.di

import com.example.catalog.ui.catalog.CatalogFragment
import com.example.catalog.ui.detail.MealDetailDialogFragment
import com.example.catalog.ui.detail.MealsDetailViewModelFactory
import com.example.catalog.ui.meals.MealsFragment
import com.example.core.di.CoreComponent
import dagger.Component

@ActivityScope
@Component(modules = [ViewModelModule::class], dependencies = [CoreComponent::class])
interface FoodComponent{
    fun inject(fragment: CatalogFragment)
    fun inject(fragment: MealsFragment)
    fun inject(fragment: MealDetailDialogFragment)


    @Component.Builder
    interface Builder{
        fun deps(coreComponent: CoreComponent): Builder
        fun build(): FoodComponent
    }
}

