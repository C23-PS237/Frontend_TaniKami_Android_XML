package com.bangkit.tanikami_xml.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.bangkit.tanikami_xml.R
import com.bangkit.tanikami_xml.databinding.ActivityMainBinding
import com.bangkit.tanikami_xml.ui.home.HomeViewModel
import com.bangkit.tanikami_xml.ui.user.UserViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen().apply {
            setKeepOnScreenCondition {
                lifecycleScope.launch {
                    delay(3000)
                }
                false
            }
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val navView: BottomNavigationView = binding.navBotView

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

    override fun onStart() {
        super.onStart()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            @RequiresApi(Build.VERSION_CODES.TIRAMISU)
            if (!isAllPermissionGranted()) {
                ActivityCompat.requestPermissions(
                    this@MainActivity,
                    REQUIRED_PERMISSION_AD13,
                    REQUEST_CODE_PERMISSION
                )
            }
        } else {
            if (!isAllPermissionGranted()){
                ActivityCompat.requestPermissions(
                    this@MainActivity,
                    REQUIRED_PERMISSION,
                    REQUEST_CODE_PERMISSION
                )
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSION) {
            if (!isAllPermissionGranted()) {
                Toast.makeText(this@MainActivity, getString(R.string.permission), Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
    private fun isAllPermissionGranted(): Boolean {
        @RequiresApi(Build.VERSION_CODES.TIRAMISU)
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
            return REQUIRED_PERMISSION.all {
                ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
            }
        } else {
            return REQUIRED_PERMISSION_AD13.all {
                ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
            }
        }
    }

    companion object {
        private val REQUIRED_PERMISSION = arrayOf(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE)

        @RequiresApi(Build.VERSION_CODES.TIRAMISU)
        private val REQUIRED_PERMISSION_AD13 = arrayOf(Manifest.permission.CAMERA, Manifest.permission.READ_MEDIA_IMAGES)

        private const val REQUEST_CODE_PERMISSION = 10
    }
}