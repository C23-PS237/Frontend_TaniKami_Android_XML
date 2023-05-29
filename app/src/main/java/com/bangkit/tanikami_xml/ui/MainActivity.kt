package com.bangkit.tanikami_xml.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.bangkit.tanikami_xml.R
import com.bangkit.tanikami_xml.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var stat = true

        installSplashScreen().apply {
            setKeepOnScreenCondition {
                lifecycleScope.launch {
                    delay(3000)
                    stat = false
                }
                stat
            }
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val navView: BottomNavigationView = binding.navBotView
        //navView.itemActiveIndicatorColor = getColorStateList(androidx.appcompat.R.color.primary_dark_material_dark)
        //navView.itemIconTintList = null

//        if (navView.isPressed) {
//            navView.itemTextColor = getColorStateList(R.color.green700)
//            navView.itemIconTintList = getColorStateList(R.color.green700)
//        } else {
//            navView.itemTextColor = getColorStateList(R.color.green200)
//            navView.itemIconTintList = getColorStateList(R.color.green200)
//        }
        // destinationRoute.id != R.id.nav_home || destinationRoute.id != R.id.nav_article || destinationRoute.id != R.id.nav_detection || destinationRoute.id != R.id.nav_profile

        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        navController.addOnDestinationChangedListener { _, destinationRoute, _ ->
            if (destinationRoute.id == R.id.detailFragment || destinationRoute.id == R.id.onBoardingFragment || destinationRoute.id == R.id.loginFragment || destinationRoute.id == R.id.registerFragment) {
                binding.navBotView.visibility = View.GONE
            } else {
                binding.navBotView.visibility = View.VISIBLE
            }
        }

        val appbarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_article, R.id.nav_detection, R.id.nav_profile
            )
        )

        setupActionBarWithNavController(navController, appbarConfiguration)
        navView.setupWithNavController(navController)
    }
}