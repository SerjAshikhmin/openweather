package ru.ashihmin.weatherdemo

import android.os.Bundle
import androidx.fragment.app.FragmentManager
import dagger.android.support.DaggerAppCompatActivity
import ru.ashihmin.weatherdemo.databinding.ActivityMainBinding
import ru.ashihmin.weatherdemo.presentation.WeatherDisplayFragment

class MainActivity : DaggerAppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        openToWeatherDisplayFragment()
    }

    private fun openToWeatherDisplayFragment() {
        val manager: FragmentManager = supportFragmentManager
        val transaction = manager.beginTransaction()
        transaction.add(R.id.main_container, WeatherDisplayFragment(), WeatherDisplayFragment.TAG)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

}
