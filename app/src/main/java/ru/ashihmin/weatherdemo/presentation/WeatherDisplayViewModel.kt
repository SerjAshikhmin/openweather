package ru.ashihmin.weatherdemo.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import ru.ashihmin.weatherdemo.data.api.model.MainDto
import ru.ashihmin.weatherdemo.data.api.model.WeatherDto
import ru.ashihmin.weatherdemo.data.api.model.WindDto
import ru.ashihmin.weatherdemo.data.repository.WeatherRepository
import javax.inject.Inject

class WeatherDisplayViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository
) : ViewModel() {

    var currentWeather: StateFlow<WeatherState?>? = null

    suspend fun getWeatherByCoords(longitude: Double?, latitude: Double?) {
        if (longitude != null && latitude != null) {
            currentWeather = weatherRepository.getWeatherByCoords(latitude, longitude).map {
                it?.toUiState()
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = WeatherState.Loading,
            )
        }
    }
}

sealed interface WeatherState {

    data object Loading: WeatherState

    data class Success(
        val weather: List<WeatherDto>,
        val main: MainDto,
        val wind: WindDto,
        val city: String
    ): WeatherState
}
