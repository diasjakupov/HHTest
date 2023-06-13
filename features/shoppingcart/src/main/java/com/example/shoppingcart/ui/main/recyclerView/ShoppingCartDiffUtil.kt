package com.example.shoppingcart.ui.main.recyclerView

import androidx.recyclerview.widget.DiffUtil
import com.example.core.models.Identifiable
import com.example.core.models.MealEntry

class ShoppingCartDiffUtil
    (
    private val oldList: List<Pair<MealEntry, Int>>,
    private val newList: List<Pair<MealEntry, Int>>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].first.id == newList[newItemPosition].first.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}
