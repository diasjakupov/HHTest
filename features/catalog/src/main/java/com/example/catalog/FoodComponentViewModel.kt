package com.example.catalog

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.example.catalog.data.di.DaggerFoodComponent
import com.example.core.di.DaggerCoreComponent
import com.example.core.di.DataModule

class FoodComponentViewModel(application: Application) : AndroidViewModel(application) {
    private val coreComponent = DaggerCoreComponent.builder()
        .addModule(DataModule(context = application.applicationContext)).build()
    val catalogComponent = DaggerFoodComponent.builder()
        .deps(coreComponent)
        .build()
}