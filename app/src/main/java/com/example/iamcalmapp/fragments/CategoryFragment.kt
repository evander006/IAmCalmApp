package com.example.iamcalmapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.iamcalmapp.R
import com.example.iamcalmapp.adapters.CategoryAdapter
import com.example.iamcalmapp.data.Category
import com.example.iamcalmapp.databinding.FragmentCategoryBinding


class CategoryFragment : Fragment() {
    private lateinit var binding: FragmentCategoryBinding
    private lateinit var categoryList: ArrayList<Category>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentCategoryBinding.inflate(layoutInflater)
        categoryList=arguments?.getParcelableArrayList("category") ?: arrayListOf()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter= CategoryAdapter(categoryList)
        binding.recView.layoutManager= LinearLayoutManager(context)
        binding.recView.adapter=adapter

    }



}