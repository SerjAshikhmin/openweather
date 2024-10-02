package ru.ashihmin.weatherdemo.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import ru.ashihmin.weatherdemo.data.api.OpenWeatherMapApi
import ru.ashihmin.weatherdemo.data.api.model.WeatherResponse
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val weatherApi: OpenWeatherMapApi
): WeatherRepository {

    override suspend fun getWeatherByCoords(lat: Double, lon: Double): Flow<WeatherResponse?> {
        return flow {
            emit(weatherApi.getWeatherByCoords(lat, lon)?.body())
        }//.catch { e -> e.printStackTrace() }
    }
}
