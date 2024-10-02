package ru.ashihmin.weatherdemo

import android.app.Application
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import ru.ashihmin.weatherdemo.di.ApplicationComponent
import ru.ashihmin.weatherdemo.di.DaggerApplicationComponent
import javax.inject.Inject


class WeatherApplication : Application(), HasAndroidInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    //lateinit var appComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()

        /*DaggerApplicationComponent.create()
            .inject(this)*/

        DaggerApplicationComponent
            .builder()
            .application(this)
            .build()
            .inject(this)
    }

    override fun androidInjector(): AndroidInjector<Any> {
        return dispatchingAndroidInjector
    }

    companion object {
        //lateinit var appComponent: ApplicationComponent
    }

}
