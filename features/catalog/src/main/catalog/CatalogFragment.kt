package com.example.catalog.catalog

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.catalog.R
import com.example.catalog.catalog.recyclerView.CategoryAdapter

class CatalogFragment : Fragment() {

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

        categoryRV.setHasFixedSize(true)
        categoryRV.adapter = adapter
        categoryRV.layoutManager = LinearLayoutManager(this.context)

    }


}