package com.example.shoppingcart.ui.main.di

import com.example.core.repository.Repository
import com.example.shoppingcart.ui.main.ShoppingCartViewModel
import com.example.shoppingcart.ui.main.ShoppingCartViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class ViewModelModule {

    @ActivityScope
    @Provides
    fun provideShoppingViewModelFactory(
        repository: Repository
    ): ShoppingCartViewModelFactory{
        return ShoppingCartViewModelFactory(repository)
    }

}