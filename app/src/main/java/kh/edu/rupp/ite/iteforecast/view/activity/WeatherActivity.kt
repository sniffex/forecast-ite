// ui/WeatherActivity.kt
package kh.edu.rupp.ite.iteforecast.view.activity

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment
import kh.edu.rupp.ite.iteforecast.R
import kh.edu.rupp.ite.iteforecast.databinding.ActivityWeatherBinding
import kh.edu.rupp.ite.iteforecast.view.fragment.HomeFragment
import kh.edu.rupp.ite.iteforecast.view.fragment.LocationFragment
import kh.edu.rupp.ite.iteforecast.viewmodel.WeatherViewModel


class WeatherActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWeatherBinding
    private val viewModel: WeatherViewModel by viewModels ()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val controller = WindowInsetsControllerCompat(window, window.decorView)
            controller.show(WindowInsetsCompat.Type.systemBars() and WindowInsetsCompat.Type.navigationBars())
        } else {
            // Legacy approach for older APIs
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
        }

        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(window, window.decorView).systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE

        binding = ActivityWeatherBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(HomeFragment())

        binding.activityWeatherBottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.iconHome -> replaceFragment(HomeFragment())
                R.id.iconLocation -> replaceFragment(LocationFragment())
                else -> {

                }
            }

            true
        }

    }


    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.activityWeatherFragmentContainer, fragment)
        transaction.commit()
    }


}
