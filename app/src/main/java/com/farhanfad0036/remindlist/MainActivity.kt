package com.farhanfad0036.remindlist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.farhanfad0036.remindlist.navigation.SetupNavGraph
import com.farhanfad0036.remindlist.ui.theme.screen.MainScreen
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