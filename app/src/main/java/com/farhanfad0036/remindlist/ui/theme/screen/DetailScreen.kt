package com.farhanfad0036.remindlist.ui.theme.screen

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.farhanfad0036.remindlist.R
import com.farhanfad0036.remindlist.ui.theme.theme.RemindListTheme
import com.farhanfad0036.remindlist.util.ViewModelFactory
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

const val KEY_ID_PEKERJAAN = "idPekerjaan"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(navController: NavHostController, id: Long? = null) {
    val context = LocalContext.current
    val factory = ViewModelFactory(context)
    val viewModel: DetailViewModel = viewModel(factory = factory)

    var judul by remember { mutableStateOf("") }
    var deskripsi by remember { mutableStateOf("") }
    var deadline by remember { mutableStateOf(0L) }
    var selesai by remember { mutableStateOf("Belum selesai") }
    var showDialog by remember { mutableStateOf(false) }


    LaunchedEffect(Unit) {
        if (id == null) return@LaunchedEffect
        val data = viewModel.getPekerjaan(id) ?: return@LaunchedEffect
        judul = data.judul
        deskripsi = data.deskripsi
        deadline = data.deadline
        selesai = data.selesai
    }

    Scaffold (
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = {navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.kembali),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                title = {
                    if (id == null)
                        Text(text = stringResource(id = R.string.tambah_pekerjaan))
                    else
                        Text(text = stringResource(id = R.string.edit))
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = Color.Red,
                    titleContentColor = Color.White,
                ),
                actions = {
                    IconButton(onClick = {
                        if (judul == "" || deskripsi == "" || deadline == 0L) {
                            Toast.makeText(context, R.string.invalid, Toast.LENGTH_LONG).show()
                            return@IconButton
                        }
                        if (id == null) {
                            viewModel.insert(judul, deskripsi, deadline, selesai)
                        } else {
                            viewModel.update(id, judul, deskripsi, deadline, selesai)
                        }
                        navController.popBackStack()}) {
                        Icon(
                            imageVector = Icons.Outlined.Check,
                            contentDescription = stringResource(R.string.simpan),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                    if (id != null ) {
                        DeleteAction {
                            showDialog = true
                        }
                    }
                }
            )
        }
    ) { padding ->
        FormPekerjaan(
            judul = judul,
            onJudulChange = { judul = it },
            deskripsi = deskripsi,
            onDeskripsiChange = { deskripsi = it },
            deadline = deadline,
            onDeadlineChange = {deadline = it},
            selesai = selesai,
            onSelesaiChange = {selesai = it},
            modifier = Modifier.padding(padding),
            isEditMode = id != null
        )
        if (id != null && showDialog) {
            DisplayAlertDialog(
                onDismissRequest = {showDialog = false}
            ) {
                showDialog = false
                viewModel.softDelete(id)
                navController.popBackStack()
            }
        }
    }
}

@Composable
fun DeleteAction(delete: () -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    IconButton(onClick = {expanded = true }) {
        Icon(
            imageVector = Icons.Filled.MoreVert,
            contentDescription = stringResource(R.string.lainnya),
            tint = MaterialTheme.colorScheme.primary
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = {expanded = false}
        ) {
            DropdownMenuItem(
                text = {
                    Text(text = stringResource(id = R.string.hapus))
                },
                onClick = {
                    expanded = false
                    delete()
                }
            )
        }
    }
}

@Composable
fun FormPekerjaan(
    judul: String, onJudulChange: (String) -> Unit,
    deskripsi: String, onDeskripsiChange: (String) -> Unit,
    deadline: Long, onDeadlineChange: (Long) -> Unit,
    selesai: String, onSelesaiChange: (String) -> Unit,
    modifier: Modifier,
    isEditMode: Boolean
) {
    val listSelesai = if (isEditMode) {
        listOf("Belum selesai", "Selesai")
    } else {
        listOf("Belum selesai")
    }

    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)
    val hour = calendar.get(Calendar.HOUR_OF_DAY)
    val minute = calendar.get(Calendar.MINUTE)

    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }
    var tempDate by remember { mutableStateOf(0L) }

    Column (
        modifier = modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            value = judul,
            onValueChange = {onJudulChange(it) },
            label = { Text(text = stringResource(R.string.judul)) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = deskripsi,
            onValueChange = {onDeskripsiChange(it) },
            label = { Text(text = stringResource(R.string.dekskripsi)) },
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Sentences,
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
        )
        Button(onClick = { showDatePicker = true },
            modifier = Modifier.fillMaxWidth()) {
            val label = if (deadline > 0L) {
                SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault())
                    .format(Date(deadline))
            } else {
                "Pilih Deadline"
            }
            Text(text = label)
        }

        if (showDatePicker) {
            DatePickerDialog(
                context,
                { _, y, m, d ->
                    calendar.set(y, m, d)
                    calendar.set(Calendar.HOUR_OF_DAY, 0)
                    calendar.set(Calendar.MINUTE, 0)
                    calendar.set(Calendar.SECOND, 0)
                    calendar.set(Calendar.MILLISECOND, 0)
                    tempDate = calendar.timeInMillis
                    showDatePicker = false
                    showTimePicker = true
                },
                year,
                month,
                day
            ).apply {
                datePicker.minDate = System.currentTimeMillis() - 1000
            }.show()
        }

        if (showTimePicker) {
            TimePickerDialog(
                context,
                { _, h, min ->
                    calendar.timeInMillis = tempDate
                    calendar.set(Calendar.HOUR_OF_DAY, h)
                    calendar.set(Calendar.MINUTE, min)

                    val selectedTime = calendar.timeInMillis
                    if (selectedTime < System.currentTimeMillis()) {
                        Toast.makeText(context, "Tidak bisa memilih waktu yang sudah lewat", Toast.LENGTH_SHORT).show()
                        showTimePicker = false
                        return@TimePickerDialog
                    }
                    onDeadlineChange(selectedTime)
                    showTimePicker = false

                },
                hour, minute, true
            ).show()
        }
        Column (
            modifier =Modifier
                .fillMaxWidth()
                .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(4.dp))
                .padding(8.dp)
        ) {
            listSelesai.forEach{ item ->
                Row (
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .selectable(
                            selected = (item == selesai),
                            onClick ={ onSelesaiChange(item) }
                        )
                        .padding(vertical = 4.dp)
                ) {
                    RadioButton(
                        selected = (item == selesai),
                        onClick = {onSelesaiChange(item) }
                    )
                    Text(
                        text = item,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun DetailScreenPreview() {
    RemindListTheme {
        DetailScreen(rememberNavController())
    }
}