package com.farhanfad0036.remindlist.ui.theme.screen

import androidx.lifecycle.ViewModel
import com.farhanfad0036.remindlist.model.Pekerjaan
import java.text.SimpleDateFormat
import java.util.Locale

//class MainViewModel : ViewModel() {
//    val data = listOf(
//        Pekerjaan(
//            1,
//            "Challenge 10 Mobpro",
//            "melanjutkan tadi yang dikelas belum selesai",
//            "2025-05-10",
//            false,
//            false
//        ),
//        Pekerjaan(
//            2,
//            "Challenge 10 Mobpro",
//            "melanjutkan tadi yang dikelas belum selesai",
//            "2025-05-10",
//            false,
//            false
//        ),
//        Pekerjaan(
//            3,
//            "Challenge 10 Mobpro",
//            "melanjutkan tadi yang dikelas belum selesai",
//            "2025-05-10",
//            false,
//            false
//        ),
//        Pekerjaan(
//            4,
//            "Challenge 10 Mobpro",
//            "melanjutkan tadi yang dikelas belum selesai",
//            "2025-05-10",
//            false,
//            false
//        ),
//        Pekerjaan(
//            5,
//            "Challenge 10 Mobpro",
//            "melanjutkan tadi yang dikelas belum selesai",
//            "2025-05-10",
//            false,
//            false
//        ),
//        Pekerjaan(
//            6,
//            "Challenge 10 Mobpro",
//            "melanjutkan tadi yang dikelas belum selesai",
//            "2025-05-10",
//            false,
//            false
//        ),
//        Pekerjaan(
//            7,
//            "Challenge 10 Mobpro",
//            "melanjutkan tadi yang dikelas belum selesai",
//            "2025-05-10",
//            false,
//            false
//        ),
//        Pekerjaan(
//            8   ,
//            "Challenge 10 Mobpro",
//            "melanjutkan tadi yang dikelas belum selesai",
//            "2025-05-10",
//            false,
//            false
//        )
//    )
//    fun getPekerjaan(id: Long): Pekerjaan? {
//        return data.find { it.id == id }
//    }
//}

class MainViewModel : ViewModel() {

    private val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    private val deadlineMillis = formatter.parse("2025-05-10")!!.time

    val data = listOf(
        Pekerjaan(
            1,
            "Challenge 10 Mobpro",
            "melanjutkan tadi yang dikelas belum selesai",
            deadlineMillis,
            "Belum selesai"
        ),
        Pekerjaan(
            2,
            "Challenge 10 Mobpro",
            "melanjutkan tadi yang dikelas belum selesai",
            deadlineMillis,
            "Belum selesai"
        ),
        Pekerjaan(
            3,
            "Challenge 10 Mobpro",
            "melanjutkan tadi yang dikelas belum selesai",
            deadlineMillis,
            "Belum selesai"
        ),
        Pekerjaan(
            4,
            "Challenge 10 Mobpro",
            "melanjutkan tadi yang dikelas belum selesai",
            deadlineMillis,
            "Belum selesai"
        ),
        Pekerjaan(
            5,
            "Challenge 10 Mobpro",
            "melanjutkan tadi yang dikelas belum selesai",
            deadlineMillis,
            "Belum selesai"
        ),
        Pekerjaan(
            6,
            "Challenge 10 Mobpro",
            "melanjutkan tadi yang dikelas belum selesai",
            deadlineMillis,
            "Belum selesai"
        ),
        Pekerjaan(
            7,
            "Challenge 10 Mobpro",
            "melanjutkan tadi yang dikelas belum selesai",
            deadlineMillis,
            "Belum selesai"
        ),
        Pekerjaan(
            8,
            "Challenge 10 Mobpro",
            "melanjutkan tadi yang dikelas belum selesai",
            deadlineMillis,
            "Belum selesai"
        )
    )

    fun getPekerjaan(id: Long): Pekerjaan? {
        return data.find { it.id == id }
    }
}
