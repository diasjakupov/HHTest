package com.example.catalog.ui.meals

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.catalog.FoodComponentViewModel
import com.example.catalog.R
import com.example.catalog.catalog.recyclerView.GridSpacingItemDecoration
import com.example.catalog.ui.catalog.CatalogViewModel
import com.example.catalog.ui.meals.recyclerView.MealsAdapter
import com.example.core.db.network.FoodResult
import com.example.core.models.Category
import com.example.core.models.MealEntry
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class MealsFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: MealsViewModelFactory
    private lateinit var viewModel: MealsViewModel
    private val args: MealsFragmentArgs by navArgs()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Initialize viewModels
        ViewModelProvider(this)[FoodComponentViewModel::class.java].catalogComponent.inject(
            this
        )
        viewModel = (viewModels<MealsViewModel> { viewModelFactory }).value
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_meals, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = MealsAdapter()
        val mealsRV = view.findViewById<RecyclerView>(R.id.mealsRV)


        setUpTags(view)
        setUpRV(adapter, mealsRV)
        setUpToolbar(view)
        observeData(view,adapter)



    }

    private fun observeData(view: View, adapter: MealsAdapter){
        val progressBar = view.findViewById<ProgressBar>(R.id.loadingProgress)

        lifecycleScope.launch {
            viewModel.getAllMeals()

            viewModel.mealsList.collectLatest {
                when (it) {
                    is FoodResult.Error -> {
                        progressBar.visibility = View.GONE

                        Log.e("TAG ERROR", it.message)
                    }
                    is FoodResult.Loading -> {
                        Log.e("TAG LOADING", "Loading...")
                        progressBar.visibility = View.VISIBLE
                    }
                    is FoodResult.Success -> {
                        progressBar.visibility = View.GONE
                        if (it.data?.dishes != null) {
                            Log.e("TAG SUCCESS", "${it.data!!.dishes}")
                            adapter.updateNewData(it.data!!.dishes as ArrayList<MealEntry>)
                        }
                    }
                }
            }
        }
    }

    private fun setUpToolbar(view: View){
        view.findViewById<TextView>(R.id.fragments_screen_toolbar_title).text = args.categoryName

        view.findViewById<ConstraintLayout>(R.id.meals_toolbar)?.let {
            it.findViewById<ImageView>(R.id.fragments_screen_toolbar_back_arrow).setOnClickListener {
                findNavController().navigateUp()
            }
        }
    }

    private fun setUpTags(view: View){
        val chipsGroup = view.findViewById<ChipGroup>(R.id.meals_tags)
        chipsGroup.isSingleSelection = true

        lifecycleScope.launch {
            viewModel.filterValues.collectLatest {
                it.forEach {text->
                    addChip(text, chipsGroup)
                }
            }
        }
    }

    private fun setUpRV(adapter: MealsAdapter, mealsRV: RecyclerView){
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

        chip.setOnCheckedChangeListener { compoundButton, checked ->
            if(checked){
                viewModel.filterMealsList(compoundButton.text as String)
            }
        }
        chipGroup.addView(chip)
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModelStore.clear()
    }
}