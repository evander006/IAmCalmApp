package com.example.iamcalmapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.iamcalmapp.databinding.FragmentMainBinding
import com.google.firebase.auth.FirebaseAuth


class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentMainBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()
        binding.next.setOnClickListener {
            if (auth.currentUser == null) {
                findNavController().navigate(R.id.action_mainFragment_to_signUpFragment)
            } else {
                findNavController().navigate(R.id.action_mainFragment_to_homeFragment2)
            }
        }
    }

}