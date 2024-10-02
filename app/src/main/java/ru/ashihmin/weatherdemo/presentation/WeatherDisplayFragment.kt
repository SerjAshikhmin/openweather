package ru.ashihmin.weatherdemo.presentation

import android.Manifest
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import dagger.android.support.DaggerFragment
import ru.ashihmin.weatherdemo.R
import ru.ashihmin.weatherdemo.data.api.model.MainType
import ru.ashihmin.weatherdemo.data.api.model.WeatherResponse
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

                viewModel.getWeatherByCoords(longitude, latitude)
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
        subscribeToViewModel()

        super.onViewCreated(view, savedInstanceState)
    }

    private fun subscribeToViewModel() {
        viewModel.currentWeather.observe(viewLifecycleOwner, this::setCurrentWeather)
    }

    private fun setCurrentWeather(weatherResponse: WeatherResponse?) {
        with(binding) {
            city.text = weatherResponse?.city
            temperature.text = getFormattedCelsiusTemp(weatherResponse)
            humidity.text = getConvertedHumidity(weatherResponse)
            wind.text = getConvertedWind(weatherResponse)
            pressure.text = getConvertedPressure(weatherResponse)
            setWeatherIcon(weatherResponse?.weather?.get(0)?.mainType)
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

    private fun getConvertedWind(weatherResponse: WeatherResponse?): String? {
        val formattedWind = String.format("%.1f", weatherResponse?.wind?.speed)
        return context?.getString(
            R.string.wind_string,
            formattedWind
        )
    }

    private fun getConvertedPressure(weatherResponse: WeatherResponse?) =
        context?.getString(
            R.string.pressure_string,
            (weatherResponse?.main?.pressure?.times(0.750063755419211))?.toInt().toString()
        )

    private fun getConvertedHumidity(weatherResponse: WeatherResponse?) =
        context?.getString(
            R.string.humidity_string,
            weatherResponse?.main?.humidity.toString()
        )

    private fun getFormattedCelsiusTemp(weatherResponse: WeatherResponse?): String {
        val tempValue = weatherResponse?.main?.temperature
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
