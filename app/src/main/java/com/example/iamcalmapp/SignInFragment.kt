package com.example.iamcalmapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.iamcalmapp.databinding.FragmentSignInBinding
import com.google.firebase.auth.FirebaseAuth


class SignInFragment : Fragment() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: FragmentSignInBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentSignInBinding.inflate(layoutInflater)
        auth= FirebaseAuth.getInstance()
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        regUser()
    }

    private fun regUser(){
        binding.registerTextView.setOnClickListener {
            findNavController().navigate(R.id.action_signInFragment_to_signUpFragment)
        }

        binding.loginButton.setOnClickListener {
            val email=binding.emailEditText.text.toString().trim()
            val pass=binding.passwordEditText.text.toString().trim()
            if (email.isNotEmpty()&&pass.isNotEmpty()){
                auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener({
                    if (it.isSuccessful){
                        Toast.makeText(context, "Успешный вход", Toast.LENGTH_LONG).show()
                        findNavController().navigate(R.id.action_signInFragment_to_homeFragment)
                    }else{
                        Toast.makeText(context, it.exception?.message, Toast.LENGTH_LONG).show()
                    }
                })
            }else{
                Toast.makeText(context, "Поле email или password пустое", Toast.LENGTH_LONG).show()
            }

        }
    }

}