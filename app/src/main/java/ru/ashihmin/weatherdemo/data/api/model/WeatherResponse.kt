package ru.ashihmin.weatherdemo.data.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class WeatherResponse(

    @SerialName("weather")
    val weather: List<WeatherDto>,

    @SerialName("main")
    val main: MainDto,

    @SerialName("wind")
    val wind: WindDto,

    @SerialName("name")
    val city: String
)
