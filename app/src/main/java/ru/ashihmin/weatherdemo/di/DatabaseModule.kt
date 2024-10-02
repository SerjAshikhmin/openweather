package ru.ashihmin.weatherdemo.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import ru.ashihmin.weatherdemo.data.db.AppDatabase
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(applicationContext: Context): AppDatabase {
        return Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            DATABASE_NAME
        ).build()
    }

    companion object {

        private const val DATABASE_NAME = "appDB"
    }
}
