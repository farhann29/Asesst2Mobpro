package com.farhanfad0036.remindlist.ui.theme.screen

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material.icons.filled.Restore
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.farhanfad0036.remindlist.R
import com.farhanfad0036.remindlist.model.Pekerjaan


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecycleBinScreen(
    viewModel: MainViewModel,
    navController: NavHostController


) {
    val deletedItems by viewModel.deletedData.collectAsState()

    var selectedItem by remember { mutableStateOf<Pekerjaan?>(null) }
    var actionType by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(deletedItems) {
        deletedItems.forEach{
            Log.d("RecycleBinScreen", "Deleted Items: ${deletedItems.size}")
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Data terhapus")
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = Color.Red,
                    titleContentColor = Color.White
                ),
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = stringResource(R.string.kembali),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            )
        }
    ) { padding ->
        if (deletedItems.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text(text = stringResource(R.string.list_kosong))
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                items(deletedItems) { pekerjaan ->
                    Column (
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        ListItem(
                            pekerjaan = pekerjaan,
                            onClick = {},
                            onDelete = {})
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            IconButton(onClick = {
                                selectedItem = pekerjaan
                                actionType = "restore"
                            }) {
                                Icon(
                                    imageVector = Icons.Default.Restore,
                                    contentDescription = "Pulihkan"
                                )
                            }
                            IconButton(onClick = {
                                selectedItem = pekerjaan
                                actionType = "delete"
                            }) {
                                Icon(
                                    imageVector = Icons.Default.DeleteForever,
                                    contentDescription = "Hapus Permanen"
                                )
                            }
                        }
                    }
                    HorizontalDivider()
                }
            }
        }
        if (selectedItem != null && actionType != null) {
            AlertDialog(
                onDismissRequest = {
                    selectedItem = null
                    actionType = null
                },
                title = {
                    Text(text = if (actionType == "restore") "Konfirmasi Pemulihan" else "Konfirmasi Penghapusan")
                },
                text = {
                    Text(
                        text = if (actionType == "restore")
                            "Apakah Anda yakin ingin memulihkan item ini?"
                        else
                            "Apakah Anda yakin ingin menghapus permanen item ini?"
                    )
                },
                confirmButton = {
                    TextButton(onClick = {
                        selectedItem?.let {
                            if (actionType == "restore") {
                                viewModel.restore(it.id)
                            } else {
                                viewModel.permanentDelete(it.id)
                            }
                        }
                        selectedItem = null
                        actionType = null
                    }) {
                        Text("Ya")
                    }
                },
                dismissButton = {
                    TextButton(onClick = {
                        selectedItem = null
                        actionType = null
                    }) {
                        Text("Batal")
                    }
                }
            )
        }
    }
}