package ru.ashihmin.weatherdemo.data.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class WeatherDto(

    @SerialName("main")
    val mainType: MainType,

    @SerialName("description")
    val description: String
)
