package ru.ashihmin.weatherdemo.data.api

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import ru.ashihmin.weatherdemo.data.api.model.FindCitiesResponse
import ru.ashihmin.weatherdemo.data.api.model.WeatherResponse

interface OpenWeatherMapApi {

    @GET("weather")
    fun getWeatherByCoords(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double
    ): Single<WeatherResponse>

    @GET("weather")
    fun getWeatherByCityAndCountry(
        @Query("q") cityAndCountry: String
    ): Single<WeatherResponse>

    @GET("find")
    fun findCities(
        @Query("q") city: String
    ): Single<FindCitiesResponse>

}
