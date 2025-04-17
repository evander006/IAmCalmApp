package com.example.iamcalmapp

import android.app.Fragment
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.iamcalmapp.databinding.ActivityMainBinding
import com.example.iamcalmapp.fragments.HomeFragment
import com.example.iamcalmapp.fragments.LibraryFragment
import com.example.iamcalmapp.fragments.NotesFragment
import com.example.iamcalmapp.fragments.TimerFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // 1. Inflate binding FIRST
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 2. Find NavHostFragment and get NavController
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        // 3. Setup bottom navigation
        binding.bottomNav.setupWithNavController(navController)

        // 4. Handle destination changes
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.mainFragment, R.id.signInFragment, R.id.signUpFragment -> {
                    // Hide bottom nav for auth screens
                    binding.bottomNav.visibility = View.GONE
                }
                R.id.homeFragment->{
                    binding.bottomNav.visibility = View.VISIBLE

                }
            }
        }
        binding.bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    replaceFragment(HomeFragment())
                    true
                }
                R.id.nav_timer -> {
                    replaceFragment(TimerFragment())
                    true
                }
                R.id.nav_library -> {
                    replaceFragment(LibraryFragment())
                    true
                }
                R.id.nav_notes -> {
                    replaceFragment(NotesFragment())
                    true
                }
                else -> false
            }
        }

        // 5. Handle window insets
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
    private fun replaceFragment(fr: androidx.fragment.app.Fragment){
        supportFragmentManager.beginTransaction().replace(R.id.nav_host_fragment,fr).commit()
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}