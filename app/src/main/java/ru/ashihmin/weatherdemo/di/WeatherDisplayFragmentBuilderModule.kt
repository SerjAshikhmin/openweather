package ru.ashihmin.weatherdemo.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import ru.ashihmin.weatherdemo.presentation.WeatherDisplayFragment

@Module
abstract class WeatherDisplayFragmentBuilderModule {

    @ContributesAndroidInjector
    abstract fun bindWeatherDisplayFragment(): WeatherDisplayFragment
}
