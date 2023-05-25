package com.bangkit.tanikami_xml

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.bangkit.tanikami_xml.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val navView: BottomNavigationView = binding.navBotView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        val appbarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_article, R.id.nav_detection, R.id.nav_profile
            )
        )

        setupActionBarWithNavController(navController, appbarConfiguration)
        navView.setupWithNavController(navController)
    }
}