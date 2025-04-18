package com.example.iamcalmapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.iamcalmapp.R
import com.example.iamcalmapp.adapters.LibraryAdapter
import com.example.iamcalmapp.data.libraryList
import com.example.iamcalmapp.databinding.FragmentLibraryBinding


class LibraryFragment : Fragment() {
    private lateinit var binding: FragmentLibraryBinding
    private lateinit var adapter: LibraryAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentLibraryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter= LibraryAdapter(libraryList)
        binding.recView.layoutManager= GridLayoutManager(context,2)
        binding.recView.adapter=adapter
    }
}