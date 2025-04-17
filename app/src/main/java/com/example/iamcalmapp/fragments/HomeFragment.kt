package com.example.iamcalmapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.iamcalmapp.R
import com.example.iamcalmapp.adapters.PopularCardAdapter
import com.example.iamcalmapp.data.PopularPractices
import com.example.iamcalmapp.data.videoCards
import com.example.iamcalmapp.databinding.FragmentHomeBinding


class HomeFragment : Fragment(){
    private lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentHomeBinding.inflate(layoutInflater)
        val adapter= PopularCardAdapter(videoCards)
        binding.recView.layoutManager = LinearLayoutManager(context)
        binding.recView.adapter=adapter
        return binding.root
    }

}