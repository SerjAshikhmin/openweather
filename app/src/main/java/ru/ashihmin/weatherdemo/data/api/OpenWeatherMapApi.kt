package ru.ashihmin.weatherdemo.data.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import ru.ashihmin.weatherdemo.data.api.model.FindCitiesResponse
import ru.ashihmin.weatherdemo.data.api.model.WeatherResponse

interface OpenWeatherMapApi {

    @GET("weather")
    suspend fun getWeatherByCoords(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double
    ): Response<WeatherResponse?>?

    @GET("weather")
    suspend fun getWeatherByCityAndCountry(
        @Query("q") cityAndCountry: String
    ): Response<WeatherResponse?>?

    @GET("find")
    suspend fun findCities(
        @Query("q") city: String
    ): Response<FindCitiesResponse?>?

}
