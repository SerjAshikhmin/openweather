package ru.ashihmin.weatherdemo.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import ru.ashihmin.weatherdemo.MainActivity

@Module
abstract class ActivityBuilderModule {

    @ContributesAndroidInjector(modules = [
        WeatherDisplayFragmentBuilderModule::class
    ])
    abstract fun bindMainActivity(): MainActivity
}
