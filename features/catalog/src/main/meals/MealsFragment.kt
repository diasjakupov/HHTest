package com.example.catalog.meals

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.catalog.R
import com.example.catalog.catalog.recyclerView.GridSpacingItemDecoration
import com.example.catalog.meals.recyclerView.MealsAdapter
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

class MealsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_meals, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpTags(view)


        setUpRV(view)


        view.findViewById<ConstraintLayout>(R.id.meals_toolbar)?.let {
            it.findViewById<ImageView>(R.id.fragments_screen_toolbar_back_arrow).setOnClickListener {
                findNavController().navigateUp()
            }
        }
    }

    private fun setUpTags(view: View){
        val chipsGroup = view.findViewById<ChipGroup>(R.id.meals_tags)

        //Testing values TODO add tags dynamically
        addChip("Все меню", chipsGroup)
        addChip("Салаты", chipsGroup)

        chipsGroup.setOnCheckedStateChangeListener { group, checkedIds ->
            //TODO Filter data by tags
        }
    }

    private fun setUpRV(view: View){
        val adapter = MealsAdapter()
        val mealsRV = view.findViewById<RecyclerView>(R.id.mealsRV)
        mealsRV.setHasFixedSize(true)
        mealsRV.adapter = adapter
        mealsRV.layoutManager = GridLayoutManager(this.context, 3)
        mealsRV.addItemDecoration(GridSpacingItemDecoration(3, 50, false))
    }

    private fun addChip(text: String, chipGroup: ChipGroup){
        val chip = Chip(this.context)
        chip.setChipBackgroundColorResource(R.color.bg_chip_color)
        chip.setTextColor(resources.getColor(R.color.text_chip_color))
        chip.isCheckable = true
        chip.isCheckedIconVisible = false

        chip.text = text
        chipGroup.addView(chip)
    }
}