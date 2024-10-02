package ru.ashihmin.weatherdemo.di

import dagger.Module
import dagger.Provides
import ru.ashihmin.weatherdemo.data.api.OpenWeatherMapApi
import ru.ashihmin.weatherdemo.data.repository.WeatherRepository
import ru.ashihmin.weatherdemo.data.repository.WeatherRepositoryImpl
import javax.inject.Singleton

@Module
class WeatherRepositoryModule {

    @Provides
    @Singleton
    fun provideWeatherRepository(weatherMapApi: OpenWeatherMapApi): WeatherRepository {
        return WeatherRepositoryImpl(weatherMapApi)
    }
}
