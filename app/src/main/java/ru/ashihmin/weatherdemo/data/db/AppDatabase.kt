package ru.ashihmin.weatherdemo.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.ashihmin.weatherdemo.data.db.model.CityDb

@Database(entities = [
    CityDb::class
], version = 1)
//@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    //abstract fun userDao(): UserDao

}
