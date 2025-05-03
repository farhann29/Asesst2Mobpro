package com.farhanfad0036.remindlist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.farhanfad0036.remindlist.navigation.SetupNavGraph
import com.farhanfad0036.remindlist.ui.theme.theme.RemindListTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RemindListTheme {
                SetupNavGraph()
            }
        }
    }
}