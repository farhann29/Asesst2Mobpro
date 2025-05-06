package com.farhanfad0036.remindlist.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.farhanfad0036.remindlist.model.Pekerjaan
import kotlinx.coroutines.flow.Flow

@Dao
interface PekerjaanDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(pekerjaan: Pekerjaan)

    @Update
    suspend fun update(pekerjaan: Pekerjaan)

    @Query("SELECT * FROM pekerjaan ORDER BY deadline ASC")
    fun getPekerjaan(): Flow<List<Pekerjaan>>

    @Query("SELECT * FROM pekerjaan WHERE id = :id")
    suspend fun getPekerjaanById(id: Long): Pekerjaan?

    @Query("UPDATE pekerjaan SET isDeleted = true WHERE id = :id")
    suspend fun softDelete(id: Long)

    @Query("UPDATE pekerjaan SET isDeleted = false WHERE id = :id")
    suspend fun restore(id: Long)

    @Query("SELECT * FROM pekerjaan WHERE isDeleted = true ORDER BY deadline ASC")
    fun getDeletedPekerjaan(): Flow<List<Pekerjaan>>

    @Query("DELETE FROM pekerjaan WHERE id = :id")
    suspend fun deleteById(id: Long)


    @Query("DELETE FROM pekerjaan WHERE isDeleted = 1")
    suspend fun deleteAllDeleted()
}
