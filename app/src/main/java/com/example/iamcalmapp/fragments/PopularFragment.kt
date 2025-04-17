package com.example.iamcalmapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import com.example.iamcalmapp.databinding.FragmentPopularBinding
import androidx.core.net.toUri


class PopularFragment : Fragment() {
    private lateinit var binding: FragmentPopularBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentPopularBinding.inflate(layoutInflater)
        val videoUrl=arguments?.getString("url")
        val uri = "android.resource://${requireContext().packageName}/raw/${videoUrl}".toUri()
        binding.video.setVideoURI(uri)
        binding.video.setMediaController(MediaController(requireContext()))
        binding.video.requestFocus()
        binding.video.start()
        return binding.root
    }


}