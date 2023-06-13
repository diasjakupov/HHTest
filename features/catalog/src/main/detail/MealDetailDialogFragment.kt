package com.example.catalog.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.catalog.R

class MealDetailDialogFragment : DialogFragment() {

    override fun getTheme(): Int {
        return R.style.RoundedCornersDialog
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_meal_detail_dialog, container, false)
    }

    override fun onStart() {
        //change the width of the dialog
        val window = requireDialog().window
        window?.setLayout((resources.displayMetrics.widthPixels*0.85).toInt(), window.attributes.height)
        super.onStart()
    }

}