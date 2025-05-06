package com.farhanfad0036.remindlist.database

import androidx.room.TypeConverter

class Converters {
    @TypeConverter
    fun fromBoolean(value: Boolean): Int = if (value) 1 else 0

    @TypeConverter
    fun toBoolean(value: Int): Boolean = value != 0
}
