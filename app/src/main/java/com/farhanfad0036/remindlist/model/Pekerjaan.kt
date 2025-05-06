package com.farhanfad0036.remindlist.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pekerjaan")
data class Pekerjaan(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val judul: String,
    val deskripsi: String,
    val deadline: Long = 0L,
    val selesai: String,
    val isDeleted: Boolean = false
)
