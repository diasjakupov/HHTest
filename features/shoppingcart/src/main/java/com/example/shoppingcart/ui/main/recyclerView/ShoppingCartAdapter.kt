package com.example.shoppingcart.ui.main.recyclerView

import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

import com.example.core.models.MealEntry
import com.example.shoppingcart.R


class ShoppingCartAdapter(
    private val onIncrease: (
        entry: MealEntry
    ) -> Unit, private val onDecrease: (entry: MealEntry) -> Unit
) : RecyclerView.Adapter<ShoppingCartAdapter.ShoppingCartItemViewHolder>() {
    private var shoppingCart: List<Pair<MealEntry, Int>> = listOf<Pair<MealEntry, Int>>()

    inner class ShoppingCartItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Pair<MealEntry, Int>) {
            val itemImage = itemView.findViewById<ImageView>(R.id.shoppingCartImage)
            val itemTitle = itemView.findViewById<TextView>(R.id.shoppingCartitemTitle)
            val price = itemView.findViewById<TextView>(R.id.shoppingCartpriceDetail)
            val weight = itemView.findViewById<TextView>(R.id.shoppingCartweightDetail)
            val amount = itemView.findViewById<TextView>(R.id.shoppingCartAmountItem)

            val decrease = itemView.findViewById<ImageButton>(R.id.shoppingCartMinus)
            val increase = itemView.findViewById<ImageButton>(R.id.shoppingCartPlus)

            Glide.with(itemView).load(item.first.image_url).into(itemImage)
            itemTitle.text = item.first.name
            price.text = "${item.first.price}₽"
            weight.text = "· ${item.first.weight}г"
            amount.text = item.second.toString()


            decrease.setOnClickListener {
                onDecrease(item.first)
            }

            increase.setOnClickListener {
                onIncrease(item.first)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingCartItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.shopping_cart_item, parent, false)
        return ShoppingCartItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return shoppingCart.size
    }

    override fun onBindViewHolder(holder: ShoppingCartItemViewHolder, position: Int) {
        holder.bind(shoppingCart[position])
    }

    fun updateNewData(rawData: MutableSet<Pair<MealEntry, Int>>) {
        shoppingCart = rawData.toList()
        Log.e("TAG ADAPTER SHOP", "$shoppingCart")
        notifyDataSetChanged()

    }
}