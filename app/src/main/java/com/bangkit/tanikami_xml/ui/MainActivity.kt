package com.bangkit.tanikami_xml.ui

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.bangkit.tanikami_xml.R
import com.bangkit.tanikami_xml.data.helper.Response
import com.bangkit.tanikami_xml.databinding.ActivityMainBinding
import com.bangkit.tanikami_xml.ui.home.HomeViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<HomeViewModel>()

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
           Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
        } else {
            // Permission denied, handle accordingly (e.g., show a message or disable related functionality)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var stat = true

        viewModel.getAllProducts().observe(this@MainActivity) {
            when (it) {
                is Response.Loading -> { stat = true }
                is Response.Success -> { stat = false }
                is Response.Error -> { stat = false }
            }
        }

        installSplashScreen().apply {
            setKeepOnScreenCondition {
                stat
            }
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        requestGalleryPermission()
        requestGalleryPermission()

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
            if (destinationRoute.id == R.id.nav_home || destinationRoute.id == R.id.nav_profile || destinationRoute.id == R.id.nav_detection || destinationRoute.id == R.id.nav_article) {
                binding.navBotView.visibility = View.VISIBLE
            } else {
                binding.navBotView.visibility = View.GONE
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

    private fun requestGalleryPermission() {
        val permission = Manifest.permission.READ_EXTERNAL_STORAGE
        requestPermissionLauncher.launch(permission)
    }

    private fun requestCameraPermission() {
        val permission = Manifest.permission.CAMERA
        requestPermissionLauncher.launch(permission)
    }
}