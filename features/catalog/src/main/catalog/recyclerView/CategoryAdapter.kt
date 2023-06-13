package com.example.catalog.catalog.recyclerView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.catalog.R
import com.example.catalog.catalog.CatalogFragmentDirections
import com.google.android.material.card.MaterialCardView

class CategoryAdapter(): RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {


    inner class CategoryViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bind(){
            val cardView = itemView.findViewById<MaterialCardView>(R.id.categoryCardView)

            //OnClick
            //TODO pass arguments
            cardView.setOnClickListener {
                val  action = CatalogFragmentDirections.actionCatalogToMealsFragment()
                itemView.findNavController().navigate(action)
            }
            //TODO SET IMAGE

            //title
            //TODO SET TITLE


        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.category_item, parent, false)
        return CategoryViewHolder(view)
    }

    override fun getItemCount(): Int {
        return 5
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind()
        holder
    }
}