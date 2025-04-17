package com.example.iamcalmapp.fragments

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import androidx.core.net.toUri
import com.example.iamcalmapp.R
import com.example.iamcalmapp.databinding.FragmentVideoLibraryBinding


class VideoLibraryFragment : Fragment() {
    private lateinit var binding: FragmentVideoLibraryBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentVideoLibraryBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val video= arguments?.getString("video")
        val uri = "android.resource://${requireContext().packageName}/raw/${video}".toUri()
        binding.video.setVideoURI(uri)
        binding.video.setMediaController(MediaController(requireContext()))
        binding.video.requestFocus()
        binding.video.start()
    }
}