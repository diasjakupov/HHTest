package com.example.catalog.ui.detail

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.catalog.FoodComponentViewModel
import com.example.catalog.R
import com.example.catalog.ui.meals.MealsFragmentArgs
import com.example.catalog.ui.meals.MealsViewModel
import com.example.catalog.ui.meals.MealsViewModelFactory
import javax.inject.Inject

class MealDetailDialogFragment : DialogFragment() {
    private val args: MealDetailDialogFragmentArgs by navArgs()

    @Inject
    lateinit var viewModelFactory: MealsViewModelFactory
    private lateinit var viewModel: MealsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Initialize viewModels
        ViewModelProvider(this)[FoodComponentViewModel::class.java].catalogComponent.inject(
            this
        )
        viewModel = (viewModels<MealsViewModel> { viewModelFactory }).value
    }

    override fun getTheme(): Int {
        return R.style.RoundedCornersDialog
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_meal_detail_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViewData(view)

    }

    @SuppressLint("SetTextI18n")
    private fun setUpViewData(view: View){
        val entry = args.mealDetail

        val image = view.findViewById<ImageView>(R.id.mealImageDetail)
        val title =  view.findViewById<TextView>(R.id.mealNameDetail)
        val price =  view.findViewById<TextView>(R.id.priceDetail)
        val weight =  view.findViewById<TextView>(R.id.weightDetail)
        val desc =  view.findViewById<TextView>(R.id.mealDescDetail)
        val closeBtn = view.findViewById<ImageButton>(R.id.closeBtn)
        val addToCart = view.findViewById<Button>(R.id.addToCart)



        Glide.with(view).load(entry.image_url).into(image)

        title.text = entry.name
        price.text = "${entry.price}₽"
        weight.text = "· ${entry.weight}г"
        desc.text = entry.description

        closeBtn.setOnClickListener {
            dismiss()
        }

        addToCart.setOnClickListener {
            viewModel.addToCart(args.mealDetail)
        }
    }

    override fun onStart() {
        //change the width of the dialog
        val window = requireDialog().window
        window?.setLayout((resources.displayMetrics.widthPixels*0.85).toInt(), window.attributes.height)
        super.onStart()
    }

}