package ru.ashihmin.weatherdemo.data.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.ashihmin.weatherdemo.presentation.WeatherState.Success

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
) {
    fun toUiState(): Success {
        return Success(
            weather = weather,
            main = main,
            wind = wind,
            city = city
        )
    }
}
