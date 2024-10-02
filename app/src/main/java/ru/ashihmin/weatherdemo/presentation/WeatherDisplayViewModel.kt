package ru.ashihmin.weatherdemo.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import ru.ashihmin.weatherdemo.data.api.OpenWeatherMapApi
import ru.ashihmin.weatherdemo.data.api.model.WeatherResponse
import javax.inject.Inject

class WeatherDisplayViewModel @Inject constructor(
    private val openWeatherMapApi: OpenWeatherMapApi
) : RxViewModel() {

    private var _currentWeather: MutableLiveData<WeatherResponse> =
        MutableLiveData()
    val currentWeather: LiveData<WeatherResponse> = _currentWeather

    fun getWeatherByCoords(longitude: Double?, latitude: Double?) {
        if (longitude != null && latitude != null) {
            openWeatherMapApi.getWeatherByCoords(latitude, longitude)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onSuccess = {
                        _currentWeather.postValue(it)
                    },
                    onError = {
                        println(it.printStackTrace())
                    }
                )
                .addTo(disposables)
        }
    }

}
