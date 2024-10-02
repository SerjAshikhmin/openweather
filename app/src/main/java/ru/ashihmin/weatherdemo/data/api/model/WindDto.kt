package ru.ashihmin.weatherdemo.data.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class WindDto(

    @SerialName("speed")
    val speed: Double,
)
