package com.example.shoppingcart.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.core.models.MealEntry
import com.example.shoppingcart.R
import com.example.shoppingcart.ui.main.recyclerView.ShoppingCartAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class ShoppingCartFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ShoppingCartViewModelFactory
    private lateinit var viewModel: ShoppingCartViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ViewModelProvider(this)[ShoppingComponentViewModel::class.java].shoppingComponent.inject(
            this
        )
        viewModel = (viewModels<ShoppingCartViewModel> { viewModelFactory }).value
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val shopRV = view.findViewById<RecyclerView>(R.id.shoppingRV)
        val adapter = ShoppingCartAdapter(onIncrease = {
            viewModel.increaseAmount(it)
        }, onDecrease = {
            viewModel.decreaseAmount(it)
        })
        setUpRV(shopRV, adapter)
        observeShoppingCart(view, adapter)
        view.findViewById<TextView>(R.id.currentDate).text = viewModel.getCurrentTime()

    }


    private fun setUpRV(shopRV: RecyclerView, adapter: ShoppingCartAdapter) {
        shopRV.setHasFixedSize(true)
        shopRV.adapter = adapter
        shopRV.layoutManager = LinearLayoutManager(this.context)
    }


    private fun observeShoppingCart(view: View, adapter: ShoppingCartAdapter) {
        viewModel.getShoppingCart()
        lifecycleScope.launch {
            viewModel.shoppingCart.collectLatest {
                adapter.updateNewData(it)
            }
        }

        lifecycleScope.launch {
            viewModel.totalPrice.collectLatest {
                Log.e("TAG PAY", it.toString())
                val button = view.findViewById<Button>(R.id.shoppingPay)
                button.text = "Оплатить $it ₽"
            }
        }

    }

}