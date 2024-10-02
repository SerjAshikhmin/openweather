package ru.ashihmin.weatherdemo.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.ashihmin.weatherdemo.presentation.ViewModelFactory
import ru.ashihmin.weatherdemo.presentation.WeatherDisplayViewModel

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(WeatherDisplayViewModel::class) // PROVIDE YOUR OWN MODELS HERE
    abstract fun bindEditPlaceViewModel(weatherDisplayViewModel: WeatherDisplayViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}
