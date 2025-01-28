package com.example.projectakhirpam_a13.ui.view.event

import android.app.DatePickerDialog
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.projectakhirpam_a13.ui.custom.CostumeTopAppBar
import com.example.projectakhirpam_a13.ui.navigasi.DestinasiNavigasi
import com.example.projectakhirpam_a13.ui.viewModel.PenyediaViewModel
import com.example.projectakhirpam_a13.ui.viewModel.event.EventInsertUiEvent
import com.example.projectakhirpam_a13.ui.viewModel.event.EventInsertUiState
import com.example.projectakhirpam_a13.ui.viewModel.event.EventInsertViewModel
import kotlinx.coroutines.launch
import java.util.Calendar

object DestinasiEntryEvent: DestinasiNavigasi {
    override val route = "insert Event"
    override val titleRes = "Insert Event"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventInsertView(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: EventInsertViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiEntryEvent.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        EntryBodyEvent(
            insertUiState = viewModel.uiState,
            onEventValueChange = viewModel::EventUpdateInsertState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertEvent()
                    navigateBack()
                }
            },
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
    }
}



@Composable
fun EntryBodyEvent(
    insertUiState: EventInsertUiState,
    onEventValueChange: (EventInsertUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        FormInputEvent(
            insertUiEvent = insertUiState.insertUiEvent,
            onValueChange = onEventValueChange,
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = onSaveClick,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Simpan")
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormInputEvent(
    insertUiEvent: EventInsertUiEvent,
    modifier: Modifier = Modifier,
    onValueChange: (EventInsertUiEvent) -> Unit = {},
    enabled: Boolean = true
) {
    val context = LocalContext.current

    // DatePickerDialog initialization
    val calendar = Calendar.getInstance()
    val datePickerDialog = remember {
        DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                val formattedDate = String.format("%04d-%02d-%02d", year, month + 1, dayOfMonth)
                // Update only the tanggal field, keeping other fields the same
                onValueChange(insertUiEvent.copy(tanggalEvent = formattedDate))
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
    }


    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Each field should only update its specific value
        OutlinedTextField(
            value = insertUiEvent.namaEvent,
            onValueChange = { onValueChange(insertUiEvent.copy(namaEvent = it)) },
            label = { Text("Nama Event") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )

        OutlinedTextField(
            value = insertUiEvent.deskripsiEvent,
            onValueChange = { onValueChange(insertUiEvent.copy(deskripsiEvent = it)) },
            label = { Text("Deskripsi Event") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )

        OutlinedTextField(
            value = insertUiEvent.lokasiEvent,
            onValueChange = { onValueChange(insertUiEvent.copy(lokasiEvent = it)) },
            label = { Text("Lokasi Event") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )

        OutlinedTextField(
            value = insertUiEvent.tanggalEvent,
            onValueChange = { /* Disable direct input */ },
            label = { Text("Tanggal Event") },
            modifier = Modifier
                .fillMaxWidth()
                .clickable { datePickerDialog.show() },
            enabled = false,
            singleLine = true,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                containerColor = Color(0xFFD6ED17), // Warna latar belakang
            )
        )

        if (enabled) {
            Text(
                text = "Isi Semua Data!",
                modifier = Modifier.padding(12.dp)
            )
        }

        Divider(
            thickness = 8.dp,
            modifier = Modifier.padding(12.dp)
        )
    }
}
