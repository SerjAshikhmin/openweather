package ru.ashihmin.weatherdemo.data.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "city")
data class CityDb(

    @PrimaryKey
    val id: Long = 0,
)
