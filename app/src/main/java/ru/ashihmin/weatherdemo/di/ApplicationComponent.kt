package ru.ashihmin.weatherdemo.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import retrofit2.Retrofit
import ru.ashihmin.weatherdemo.WeatherApplication
import ru.ashihmin.weatherdemo.data.api.OpenWeatherMapApi
import ru.ashihmin.weatherdemo.data.db.AppDatabase
import ru.ashihmin.weatherdemo.presentation.WeatherDisplayViewModel
import javax.inject.Singleton


@Singleton
@Component(
    modules = [
        ContextModule::class,
        ApiModule::class,
        DatabaseModule::class,
        ViewModelModule::class,
        AndroidInjectionModule::class,
        ActivityBuilderModule::class
    ]
)
interface ApplicationComponent {

    fun getAppDatabase(): AppDatabase

    fun getRetrofit(): Retrofit

    fun getOpenWeatherMapApi(): OpenWeatherMapApi

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): ApplicationComponent
    }

    fun inject(app: WeatherApplication) // Change to your custom app class

    fun getWeatherDisplayViewModel(): WeatherDisplayViewModel
}
