package com.example.shoppingcart.ui.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.core.models.MealEntry
import com.example.core.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ShoppingCartViewModel(
    private val repository: Repository
) : ViewModel() {
    private var _shoppingCart =  MutableStateFlow(mutableSetOf<Pair<MealEntry, Int>>())
    val shoppingCart = _shoppingCart

    private var _totalPrice = MutableStateFlow(0)
    val totalPrice = _totalPrice

    fun getShoppingCart(){
        viewModelScope.launch(Dispatchers.IO){
            val data = repository.getShoppingCart()
            _shoppingCart.emit(data)
            _totalPrice.emit(getTotalPrice(data))
        }
        Log.e("TAG SHOP TATA", shoppingCart.value.toString())
    }

    fun increaseAmount(entry: MealEntry){
        viewModelScope.launch {
            val data = repository.increaseAmount(entry)
            _shoppingCart.emit(data)
            _totalPrice.emit(getTotalPrice(data))
        }
    }

    fun decreaseAmount(entry: MealEntry){
        viewModelScope.launch {
            val data = repository.decreaseAmount(entry)
            _shoppingCart.emit(data)
            _totalPrice.emit(getTotalPrice(data))
        }
    }

    private fun getTotalPrice(data: MutableSet<Pair<MealEntry, Int>>): Int{
        return data.sumOf {
            it.first.price * it.second
        }
    }

    fun getCurrentTime() = repository.getCurrentTime()

}



class ShoppingCartViewModelFactory(
    private val repository: Repository
): ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(
        modelClass: Class<T>,
        extras: CreationExtras
    ): T {
        return ShoppingCartViewModel(
            repository
        ) as T
    }
}