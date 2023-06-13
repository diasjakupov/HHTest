package com.example.catalog.data.di

import com.example.core.repository.Repository
import com.example.catalog.ui.catalog.CatalogViewModelFactory
import com.example.catalog.ui.detail.MealsDetailViewModelFactory
import com.example.catalog.ui.meals.MealsViewModelFactory
import dagger.Module
import dagger.Provides


@Module
class ViewModelModule {

    @ActivityScope
    @Provides
    fun provideCatalogViewModelFactory(
        repository: Repository
    ): CatalogViewModelFactory{
        return CatalogViewModelFactory(repository)
    }

    @ActivityScope
    @Provides
    fun provideMealsViewModelFactory(
        repository: Repository
    ): MealsViewModelFactory{
        return MealsViewModelFactory(repository)
    }

    @ActivityScope
    @Provides
    fun provideMealsDetailViewModelFactory(repository: Repository): MealsDetailViewModelFactory {
        return MealsDetailViewModelFactory(repository)
    }
}