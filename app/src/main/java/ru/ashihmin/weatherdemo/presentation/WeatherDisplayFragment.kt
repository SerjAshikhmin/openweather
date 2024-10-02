package ru.ashihmin.weatherdemo.presentation

import android.Manifest
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dagger.android.support.DaggerFragment
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import ru.ashihmin.weatherdemo.R
import ru.ashihmin.weatherdemo.data.api.model.MainType
import ru.ashihmin.weatherdemo.databinding.FragmentWeatherDisplayBinding
import java.math.RoundingMode
import javax.inject.Inject

class WeatherDisplayFragment : DaggerFragment() {

    private var _binding: FragmentWeatherDisplayBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var modelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: WeatherDisplayViewModel

    private val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                // Precise location access granted.
                println("Precise")

                val lm = activity?.getSystemService(AppCompatActivity.LOCATION_SERVICE) as LocationManager
                val location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                val longitude = location?.longitude
                val latitude = location?.latitude

                lifecycleScope.launch {
                    lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                        viewModel.getWeatherByCoords(longitude, latitude)

                        viewModel.currentWeather
                            ?.filterNotNull()
                            ?.collect { setCurrentWeather(it) }
                    }
                }

                println("longitude: $longitude, latitude: $latitude)")
            }
            permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                // Only approximate location access granted.
                println("Approximate")
            } else -> {
                // No location access granted.
                println("No location")
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWeatherDisplayBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        locationPermissionRequest.launch(arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION)
        )

        viewModel = ViewModelProvider(this, modelFactory)[WeatherDisplayViewModel::class.java]
        //subscribeToViewModel()

        super.onViewCreated(view, savedInstanceState)
    }

    /*private fun subscribeToViewModel() {
        viewModel.currentWeather.observe(viewLifecycleOwner, this::setCurrentWeather)
    }*/

    private fun setCurrentWeather(weatherState: WeatherState) {
        when (weatherState) {
            is WeatherState.Loading -> {}
            is WeatherState.Success -> {
                with(binding) {
                    city.text = weatherState.city
                    temperature.text = getFormattedCelsiusTemp(weatherState)
                    humidity.text = getConvertedHumidity(weatherState)
                    wind.text = getConvertedWind(weatherState)
                    pressure.text = getConvertedPressure(weatherState)
                    setWeatherIcon(weatherState.weather[0].mainType)
                }
            }
        }
    }

    private fun setWeatherIcon(mainType: MainType?) {
        when (mainType) {
            MainType.Clear -> binding.weatherIcon.setImageResource(R.drawable.ic_sunny)
            MainType.Clouds -> binding.weatherIcon.setImageResource(R.drawable.ic_cloudy)
            MainType.Rain, MainType.Snow -> binding.weatherIcon.setImageResource(R.drawable.ic_rainy)
            MainType.Thunderstorm -> binding.weatherIcon.setImageResource(R.drawable.ic_thunderstorm)
            else -> binding.weatherIcon.setImageResource(R.drawable.ic_cloudy)
        }
    }

    private fun getConvertedWind(weatherState: WeatherState.Success?): String? {
        val formattedWind = String.format("%.1f", weatherState?.wind?.speed)
        return context?.getString(
            R.string.wind_string,
            formattedWind
        )
    }

    private fun getConvertedPressure(weatherState: WeatherState.Success?) =
        context?.getString(
            R.string.pressure_string,
            (weatherState?.main?.pressure?.times(0.750063755419211))?.toInt().toString()
        )

    private fun getConvertedHumidity(weatherState: WeatherState.Success?) =
        context?.getString(
            R.string.humidity_string,
            weatherState?.main?.humidity.toString()
        )

    private fun getFormattedCelsiusTemp(weatherState: WeatherState.Success?): String {
        val tempValue = weatherState?.main?.temperature
            ?.minus(273.15)
            ?.toBigDecimal()?.setScale(0, RoundingMode.HALF_UP)?.toInt() ?: 0
        return if (tempValue >= 0) {
            "+${tempValue}"
        } else {
            "-${tempValue}"
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    companion object {
        const val TAG = "WeatherDisplayFragment"
    }

}
