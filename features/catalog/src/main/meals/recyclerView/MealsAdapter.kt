package com.example.catalog.meals.recyclerView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.catalog.R
import com.example.catalog.meals.MealsFragmentDirections

class MealsAdapter(): RecyclerView.Adapter<MealsAdapter.MealViewHolder>() {


    inner class MealViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bind(){
            itemView.setOnClickListener {
                val action = MealsFragmentDirections.actionMealsFragmentToMealDetailDialogFragment()
                itemView.findNavController().navigate(action)
            }
        }

        //set image
        //TODO SET IMAGE

        //title
        //TODO SET TITLE
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.meal_item, parent, false)
        return MealViewHolder(view)
    }

    override fun getItemCount(): Int {
        return 9
    }

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {
        holder.bind()
    }
}