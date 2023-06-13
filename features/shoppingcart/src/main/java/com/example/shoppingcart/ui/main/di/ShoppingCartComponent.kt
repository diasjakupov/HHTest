package com.example.shoppingcart.ui.main.di

import com.example.core.di.CoreComponent
import com.example.shoppingcart.ui.main.ShoppingCartFragment
import dagger.Component


@ActivityScope
@Component(modules = [ViewModelModule::class], dependencies = [CoreComponent::class])
interface ShoppingCartComponent{
    fun inject(fragment: ShoppingCartFragment)

    @Component.Builder
    interface Builder{
        fun deps(coreComponent: CoreComponent): Builder
        fun build(): ShoppingCartComponent
    }
}
