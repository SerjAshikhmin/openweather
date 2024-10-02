package ru.ashihmin.weatherdemo.data.repository

import kotlinx.coroutines.flow.Flow
import ru.ashihmin.weatherdemo.data.api.model.WeatherResponse

interface WeatherRepository {

    suspend fun getWeatherByCoords(
        lat: Double,
        lon: Double
    ): Flow<WeatherResponse?>
}
