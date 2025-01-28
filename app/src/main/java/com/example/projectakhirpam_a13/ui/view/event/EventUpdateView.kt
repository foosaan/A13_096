package com.example.projectakhirpam_a13.ui.view.event

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.projectakhirpam_a13.ui.custom.CostumeTopAppBar
import com.example.projectakhirpam_a13.ui.navigasi.DestinasiNavigasi
import com.example.projectakhirpam_a13.ui.viewModel.PenyediaViewModel
import com.example.projectakhirpam_a13.ui.viewModel.event.EventUpdateViewModel
import com.example.projectakhirpam_a13.ui.viewModel.event.toEvent
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale


object DestinasiUpdateEvent : DestinasiNavigasi {
    override val route = "update pasien"
    const val idEvent = "idpasien"
    val routesWithArg = "$route/{$idEvent}"
    override val titleRes = "Update Pasien"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventUpdateView(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: EventUpdateViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    val uiState = viewModel.uiState

    val formattedTangalEvent = remember(uiState.insertUiEvent?.tanggalEvent) {
        val format = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        try {
            val parsedDate = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).parse(uiState.insertUiEvent?.tanggalEvent ?: "")
            parsedDate?.let { format.format(it) } ?: uiState.insertUiEvent?.tanggalEvent // Handling null or invalid date
        } catch (e: Exception) {
            uiState.insertUiEvent?.tanggalEvent ?: "" // If parsing fails, keep the original or empty string
        }
    }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiUpdateEvent.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            // Entry Pasien Form Body
            EntryBodyEvent(
                insertUiState = uiState.copy(
                    insertUiEvent = uiState.insertUiEvent?.copy(tanggalEvent = formattedTangalEvent) ?: uiState.insertUiEvent
                ),
                onEventValueChange = { updatedValue ->
                    viewModel.EventupdateState(updatedValue)
                },
                onSaveClick = {
                    uiState.insertUiEvent?.let { insertUiEvent ->
                        coroutineScope.launch {
                            viewModel.updateEvent(
                                idEvent = viewModel.idEvent,
                                Event = insertUiEvent.toEvent()
                            )
                            navigateBack() // Navigate back after saving
                        }
                    }
                }
            )
        }
    }
}