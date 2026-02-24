package com.aifitnesscoach.android.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.aifitnesscoach.android.R
import com.aifitnesscoach.android.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupNavigation()
        handleIntent()
    }

    private fun setupNavigation() {

        // Get NavHostFragment safely
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_home)
                    as NavHostFragment

        val navController = navHostFragment.navController

        // Connect BottomNavigation with NavController
        binding.navView.setupWithNavController(navController)

        // Prevent crash on reselection
        binding.navView.setOnItemReselectedListener {
            // Do nothing
        }
    }

    private fun handleIntent() {
        val openNutrition = intent.getBooleanExtra("openNutrition", false)
        if (openNutrition) {
            navigateToFragment(R.id.navigation_nutrition)
        }
    }

    fun navigateToFragment(itemId: Int) {
        binding.navView.selectedItemId = itemId
    }
}