package com.farhanfad0036.remindlist.ui.theme.screen

import android.content.res.Configuration
import android.graphics.pdf.models.ListItem
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.viewModelFactory
import com.farhanfad0036.remindlist.R
import com.farhanfad0036.remindlist.model.Pekerjaan
import androidx.lifecycle.viewmodel.compose.viewModel



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id= R.string.app_name))
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) { innerPadding ->
        ScreenContent(Modifier.padding(innerPadding))

    }
}

@Composable
fun ScreenContent(modifier: Modifier = Modifier){
    val viewModel: MainViewModel = viewModel()
    val data = viewModel.data
//    val data = emptyList<Pekerjaan>()

    if (data.isEmpty()) {
        Column (
            modifier = modifier.fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = stringResource(id = R.string.list_kosong))
        }
    }
    else {
        LazyColumn (
            modifier = modifier.fillMaxSize()
        ){
            items(data) {
                ListItem(pekerjaan = it)
                HorizontalDivider()
            }
        }
    }
}

@Composable
fun ListItem(pekerjaan: Pekerjaan) {
    Column {
        Text(
            text = pekerjaan.judul,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontWeight = FontWeight.Bold)
        Text(
            text = pekerjaan.deskripsi,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis)
        Text(text = pekerjaan.deadline)
        Text(text = if (pekerjaan.selesai) "Selesai" else "Belum selesai")
    }
}


@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun MainScreenPreview() {
        MainScreen()

}