package com.example.projectakhirpam_a13.ui.view.tiket

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.loket.ui.viewModel.tiket.InsertTKTUiEvent
import com.example.loket.ui.viewModel.tiket.InsertTKTUiState
import com.example.loket.ui.viewModel.tiket.InsertTktViewModel
import com.example.projectakhirpam_a13.ui.custom.CostumeTopAppBar
import com.example.projectakhirpam_a13.ui.navigasi.DestinasiNavigasi
import com.example.projectakhirpam_a13.ui.viewModel.PenyediaViewModel
import kotlinx.coroutines.launch

object DestinasiInsertTiket: DestinasiNavigasi {
    override val route = "InsertTiket"
    override val titleRes = "Insert Tiket Tiket"
}
@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryMtgScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertTktViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold (
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiInsertTiket.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) {innerPadding ->
        EntryBodyMtg(
            insertMtgUiState = viewModel.TKTUiState,
            onMonitoringValueChange = viewModel::updateInsertMtgState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertMtg()
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

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun EntryBodyMtg(
    insertMtgUiState: InsertTKTUiState,
    onMonitoringValueChange: (InsertTKTUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
){
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        FormInputMtg(
            insertTKTUiEvent = insertMtgUiState.inserTKTUiEvent,
            onValueChange = onMonitoringValueChange,
            modifier = Modifier.fillMaxWidth())
        Button(
            onClick = onSaveClick,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Simpan")
        }
    }
}

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormInputMtg(
    insertTKTUiEvent: InsertTKTUiEvent,
    modifier: Modifier = Modifier,
    onValueChange: (InsertTKTUiEvent) -> Unit = {},
    InsertTKTMOdel: InsertTktViewModel = viewModel(factory = PenyediaViewModel.Factory),
    enabled: Boolean = true
){
    var Tiket = InsertTKTMOdel.TKTUiState.EventOption
    var Peserta = InsertTKTMOdel.TKTUiState.PesertaOption

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        DropDownAll(
            title = "Pilih Id Kandang",
            options = Tiket.map { it.label },
            selectedOption = insertTKTUiEvent.id_event.toString(),
            onOptionSelected = { id_kandang ->
                onValueChange(insertTKTUiEvent.copy(id_event = id_kandang.toInt()))

            }
        )

        DropDownAll(
            title = "Pilih Petugas",
            options = Peserta.map { it.label },
            selectedOption = Peserta.find { it.id_peserta == insertTKTUiEvent.id_peserta}?.label.orEmpty(),

            onOptionSelected = { id_Peserta ->
                val selected = Peserta.find { it.id_peserta.toString() == id_Peserta }
                onValueChange(insertTKTUiEvent.copy(id_peserta = selected?.id_peserta?:0))

            }
        )

        OutlinedTextField(
            value = insertTKTUiEvent.Kapasitas_tiket,
            onValueChange = {onValueChange(insertTKTUiEvent.copy(Kapasitas_tiket = it))},
            label = { Text("Tanggal Monitoring") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )

        OutlinedTextField(
            value = insertTKTUiEvent.Harga_tiket,
            onValueChange = {onValueChange(insertTKTUiEvent.copy(Harga_tiket = it))},
            label = { Text("Masukkan Hewan Sakit") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )



        if (enabled){
            Text(
                text = "Isi Semua Data!",
                modifier = Modifier.padding(12.dp)
            )
        }
    }
}

@Composable
fun DropDownAll(
    title: String,
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit,
    isError: Boolean = false,
    errorMessage: String? = null
){
    var expanded by remember { mutableStateOf(false) }
    var currentSelected by remember { mutableStateOf(selectedOption) }

    Column {
        OutlinedTextField(
            value = currentSelected,
            onValueChange = {},
            readOnly = true,
            label = { Text(text = title) },
            trailingIcon = {
                androidx.compose.material3.IconButton(onClick = { expanded = !expanded }) {
                    Icon(
                        imageVector = if (expanded) Icons.Default.ArrowDropDown else Icons.Default.ArrowDropDown,
                        contentDescription = null
                    )
                }
            },
            modifier = Modifier.fillMaxWidth()
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ){
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(text = option) },
                    onClick = {
                        onOptionSelected(option)
                        currentSelected = option
                        expanded = false
                    }
                )
            }
        }
        if (isError && errorMessage != null) {
            Text(
                text = errorMessage,
                color = Color.Red
            )

        }
    }
}
