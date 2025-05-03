package com.farhanfad0036.remindlist.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.farhanfad0036.remindlist.model.Pekerjaan
import kotlinx.coroutines.flow.Flow

@Dao
interface PekerjaanDao {
    @Insert
    suspend fun insert(pekerjaan: Pekerjaan)

    @Update
    suspend fun update(pekerjaan: Pekerjaan)

    @Query("SELECT * FROM pekerjaan ORDER BY deadline ASC")
    fun getPekerjaan(): Flow<List<Pekerjaan>>
}