package com.example.catalog.ui.catalog.recyclerView

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.example.catalog.R
import com.example.catalog.ui.FoodDiffUtil
import com.example.core.models.Category
import com.example.catalog.ui.catalog.CatalogFragmentDirections
import com.google.android.material.card.MaterialCardView


class CategoryAdapter() : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {
    private var categoryList: ArrayList<Category> = arrayListOf<Category>()

    inner class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Category) {
            val cardView = itemView.findViewById<MaterialCardView>(R.id.categoryCardView)
            val categoryTitle = itemView.findViewById<TextView>(R.id.categoryTitle)
            val bg = itemView.findViewById<ConstraintLayout>(R.id.categoryBg)

            //OnClick
            //TODO pass arguments
            cardView.setOnClickListener {
                val action = CatalogFragmentDirections.actionCatalogToMealsFragment(
                    categoryId = item.id,
                    categoryName = item.name
                )
                itemView.findNavController().navigate(action)
            }
            //TODO SET IMAGE
            Glide.with(itemView.context).load(item.image_url).centerCrop().into(object: SimpleTarget<Drawable>() {
                override fun onResourceReady(
                    resource: Drawable,
                    transition: com.bumptech.glide.request.transition.Transition<in Drawable>?
                ) {
                    bg.background = resource
                }
            })

            //title
            //TODO SET TITLE
            categoryTitle.text = item.name


        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.category_item, parent, false)
        return CategoryViewHolder(view)
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(categoryList[position])
    }

    fun updateNewData(newData: ArrayList<Category>) {
        val recipesDiffUtils=FoodDiffUtil(categoryList, newData)
        val diffUtilResult= DiffUtil.calculateDiff(recipesDiffUtils)
        categoryList=newData
        diffUtilResult.dispatchUpdatesTo(this)
    }
}