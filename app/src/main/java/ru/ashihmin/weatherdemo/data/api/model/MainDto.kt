package ru.ashihmin.weatherdemo.data.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class MainDto(

    @SerialName("temp")
    val temperature: Double,

    @SerialName("grnd_level")
    val pressure: Double,

    @SerialName("humidity")
    val humidity: Int,
)
