package com.example.catalog.ui.meals.recyclerView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.catalog.R
import com.example.catalog.ui.FoodDiffUtil
import com.example.catalog.ui.meals.MealsFragmentDirections
import com.example.core.models.MealEntry

class MealsAdapter() : RecyclerView.Adapter<MealsAdapter.MealViewHolder>() {
    private var mealsList: ArrayList<MealEntry> = arrayListOf()

    inner class MealViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: MealEntry) {
            itemView.setOnClickListener {
                val action =
                    MealsFragmentDirections.actionMealsFragmentToMealDetailDialogFragment(mealDetail = item)
                itemView.findNavController().navigate(action)
            }

            val img = itemView.findViewById<ImageView>(R.id.meal_image)
            val txt = itemView.findViewById<TextView>(R.id.meal_text)

            txt.text = item.name

            Glide.with(itemView.context).load(item.image_url).placeholder(R.drawable.img).into(img)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.meal_item, parent, false)
        return MealViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mealsList.size
    }

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {
        holder.bind(mealsList[position])
    }

    fun updateNewData(newData: ArrayList<MealEntry>) {
        val mealsDiffUtils = FoodDiffUtil(mealsList, newData)
        val diffUtilResult = DiffUtil.calculateDiff(mealsDiffUtils)
        mealsList = newData
        diffUtilResult.dispatchUpdatesTo(this)

    }
}