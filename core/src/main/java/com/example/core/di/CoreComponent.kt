package com.example.core.di

import android.content.Context
import com.example.core.db.network.FoodAPI
import com.example.core.repository.Repository
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [DataModule::class])
interface CoreComponent{
    fun foodService(): FoodAPI
    fun repository(): Repository

    @Component.Builder
    interface Builder{
        fun build(): CoreComponent
        fun addModule(module: DataModule): Builder
    }
}
