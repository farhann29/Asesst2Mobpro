package com.farhanfad0036.remindlist.ui.theme.screen

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.farhanfad0036.remindlist.R
import com.farhanfad0036.remindlist.model.Pekerjaan
import com.farhanfad0036.remindlist.navigation.Screen
import com.farhanfad0036.remindlist.util.SettingsDataStore
import com.farhanfad0036.remindlist.util.ViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavHostController) {
    val context = LocalContext.current
    val dataStore = SettingsDataStore(context)
    val factory = ViewModelFactory(context, dataStore)
    val viewModel: MainViewModel = viewModel(factory = factory)

    val showList by dataStore.layoutFlow.collectAsState(true)
    val filter by viewModel.filter.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id= R.string.app_name))
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = Color.Red,
                    titleContentColor = Color.White
                ),
                actions = {
                    var expanded by remember { mutableStateOf(false) }

                    Box{
                        IconButton(onClick = {expanded = true}) {
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_filter_list_alt_24),
                                contentDescription = "Filter",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                        val filterOptions = listOf(
                            FilterType.SEMUA,
                            FilterType.DEADLINE_TERDEKAT,
                            FilterType.TERLAMBAT,
                            FilterType.BELUM_SELESAI,
                            FilterType.SELESAI
                        )

                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = {expanded = false}
                        ) {
                            filterOptions.forEach { type ->
                                DropdownMenuItem(
                                    text = { Text(type.toDisplayString()) },
                                    onClick = {
                                        viewModel.setFilter(type)
                                        expanded = false
                                    }
                                )
                            }
                        }
                    }
                    IconButton(onClick = {
                        CoroutineScope(Dispatchers.IO).launch {
                            dataStore.saveLayout(!showList)
                        }
                    }) {
                        Icon(
                            painter = painterResource(
                                if (showList) R.drawable.baseline_grid_view_24
                                else R.drawable.baseline_view_list_24
                            ),
                            contentDescription = stringResource(
                                if (showList) R.string.grid
                                else R.string.list
                            ),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                    IconButton(onClick = {
                        navController.navigate(Screen.RecycleBin.route)
                    }) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Recycle Bin",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Screen.FormBaru.route)
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = stringResource(R.string.tambah_pekerjaan),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    ) { innerPadding ->
        ScreenContent(
            showList = showList,
            modifier = Modifier.padding(innerPadding),
            navController = navController,
            viewModel = viewModel)

    }
}

@Composable
fun ScreenContent(
    showList: Boolean,
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: MainViewModel
) {
    val data by viewModel.filteredData.collectAsState()
    val deletedData by viewModel.deletedData.collectAsState()
    val filter by viewModel.filter.collectAsState()

    val tampilkanData = data

    Column(modifier = modifier.fillMaxSize()) {
        Text(
            text = "Filter: ${filter.toDisplayString()}",
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.primary
        )

        if (tampilkanData.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = stringResource(id = R.string.list_kosong))
            }
        } else {
            if (showList) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(bottom = 84.dp)
                ) {
                    items(data) { pekerjaan ->
                        ListItem(
                            pekerjaan = pekerjaan,
                            onClick = {navController.navigate(Screen.FormUbah.withId(pekerjaan.id)) },
                            onDelete = {viewModel.softDelete(pekerjaan.id)}
                        )
                        HorizontalDivider()
                    }
                }
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(8.dp)
                ) {
                    items(data) { pekerjaan ->
                        GridItem(
                            pekerjaan = pekerjaan,
                            onClick = {navController.navigate(Screen.FormUbah.withId(pekerjaan.id)) },
                            onDelete = {viewModel.softDelete(pekerjaan.id)}
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ListItem(pekerjaan: Pekerjaan, onClick: () -> Unit, onDelete: () -> Unit) {
    val now = System.currentTimeMillis()
    val isDone = pekerjaan.selesai == "Selesai"
    val isOverdue = !isDone && pekerjaan.deadline < now

    val deadlineFormatted = remember(pekerjaan.deadline) {
        SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault())
            .format(Date(pekerjaan.deadline))
    }

    val (statusIcon, statusColor, statusText) = when {
        isDone -> Triple(Icons.Filled.CheckCircle, Color(0xFF4CAF50), "Selesai")
        isOverdue -> Triple(Icons.Filled.Warning, MaterialTheme.colorScheme.error, "Terlambat")
        else -> Triple(Icons.Filled.Schedule, Color(0xFFFF9800), "Belum selesai")
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = pekerjaan.judul,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = pekerjaan.deskripsi,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodyMedium
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Filled.CalendarToday,
                    contentDescription = "Deadline",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(Modifier.width(4.dp))
                Text(
                    text = deadlineFormatted,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = statusIcon,
                    contentDescription = statusText,
                    tint = statusColor,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(Modifier.width(4.dp))
                Text(
                    text = statusText,
                    color = statusColor,
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = if (isOverdue) FontWeight.Bold else FontWeight.Normal
                )
            }
        }
    }
}
    @Composable
    fun GridItem(pekerjaan: Pekerjaan, onClick: () -> Unit, onDelete: () -> Unit) {
        val now = System.currentTimeMillis()
        val isDone = pekerjaan.selesai == "Selesai"
        val isOverdue = !isDone && pekerjaan.deadline < now

        val deadlineFormatted = remember(pekerjaan.deadline) {
            SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault())
                .format(Date(pekerjaan.deadline))
        }

        val (statusIcon, statusColor, statusText) = when {
            isDone -> Triple(Icons.Filled.CheckCircle, Color(0xFF4CAF50), "Selesai")
            isOverdue -> Triple(Icons.Filled.Warning, MaterialTheme.colorScheme.error, "Terlambat")
            else -> Triple(Icons.Filled.Schedule, Color(0xFFFF9800), "Belum selesai")
        }

        Card(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .clickable { onClick() },
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Box(modifier = Modifier.padding(12.dp)) {
                Column(
                    modifier = Modifier.padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = pekerjaan.judul,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.titleSmall
                    )

                    Text(
                        text = pekerjaan.deskripsi,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.bodySmall
                    )

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Filled.CalendarToday,
                            contentDescription = "Deadline",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(Modifier.width(4.dp))
                        Text(
                            text = deadlineFormatted,
                            style = MaterialTheme.typography.labelSmall
                        )
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = statusIcon,
                            contentDescription = statusText,
                            tint = statusColor,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(Modifier.width(4.dp))
                        Text(
                            text = statusText,
                            color = statusColor,
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = if (isOverdue) FontWeight.Bold else FontWeight.Normal
                        )
                    }
                }
            }
        }
    }


    @Preview(showBackground = true)
    @Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
    @Composable
    fun MainScreenPreview() {
        MainScreen(navController = rememberNavController())
    }

