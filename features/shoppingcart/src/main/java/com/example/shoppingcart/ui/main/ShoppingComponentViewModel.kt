package com.example.shoppingcart.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.example.core.di.DaggerCoreComponent
import com.example.core.di.DataModule
import com.example.shoppingcart.ui.main.di.DaggerShoppingCartComponent

class ShoppingComponentViewModel(application: Application) : AndroidViewModel(application) {
    private val coreComponent =
        DaggerCoreComponent.builder().addModule(DataModule(application.applicationContext)).build()
    val shoppingComponent = DaggerShoppingCartComponent.builder()
        .deps(coreComponent)
        .build()
}