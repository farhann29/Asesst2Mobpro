package com.farhanfad0036.remindlist.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.farhanfad0036.remindlist.model.Pekerjaan

@Database(entities = [Pekerjaan::class], version = 1, exportSchema = false)
abstract class PekerjaanDb : RoomDatabase() {
    abstract val dao: PekerjaanDao

    companion object {
        @Volatile
        private var INSTANCE: PekerjaanDb? = null

        fun getInstance(context: Context): PekerjaanDb {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        PekerjaanDb::class.java,
                        "pekerjaan.db"
                    ).build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}