package com.example.catalog.ui.catalog

import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.catalog.FoodComponentViewModel
import com.example.catalog.R
import com.example.core.models.Category
import com.example.catalog.ui.catalog.recyclerView.CategoryAdapter
import com.example.core.db.network.FoodResult
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class CatalogFragment : Fragment() {
    @Inject lateinit var viewModelFactory: CatalogViewModelFactory
    private lateinit var viewModel: CatalogViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Initialize viewModels
        ViewModelProvider(this)[FoodComponentViewModel::class.java].catalogComponent.inject(
            this
        )
        viewModel = (viewModels<CatalogViewModel> { viewModelFactory }).value
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_catalog, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = CategoryAdapter()
        val categoryRV = view.findViewById<RecyclerView>(R.id.categoryRV)

        setUpRV(categoryRV, adapter)
        observeData(view, adapter)

        view.findViewById<TextView>(R.id.currentDate).text = viewModel.getCurrentTime()
    }

    private fun setUpRV(categoryRV: RecyclerView, adapter: CategoryAdapter) {
        categoryRV.setHasFixedSize(true)
        categoryRV.adapter = adapter
        categoryRV.layoutManager = LinearLayoutManager(this.context)
    }

    private fun observeData(view: View, adapter: CategoryAdapter) {
        val progressBar = view.findViewById<ProgressBar>(R.id.loadingProgress)
        lifecycleScope.launch {
            viewModel.getAllCategories()
            viewModel.categoryList.collectLatest {
                when (it) {
                    is FoodResult.Error -> {
                        progressBar.visibility = View.GONE
                        Log.e("TAG ERROR", it.message)
                    }
                    is FoodResult.Loading -> {
                        progressBar.visibility = View.VISIBLE
                    }
                    is FoodResult.Success -> {
                        progressBar.visibility = View.GONE
                        if (it.data?.сategories != null) {
                            adapter.updateNewData(it.data!!.сategories as ArrayList<Category>)
                        }
                    }
                }
            }
        }
    }


}