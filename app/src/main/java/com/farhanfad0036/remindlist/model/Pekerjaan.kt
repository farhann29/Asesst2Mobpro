package com.farhanfad0036.remindlist.model

data class Pekerjaan(
    val id: Long,
    val judul: String,
    val deskripsi: String,
    val deadline: String,
    val selesai: Boolean = false,
    val dihapus: Boolean = false
)
