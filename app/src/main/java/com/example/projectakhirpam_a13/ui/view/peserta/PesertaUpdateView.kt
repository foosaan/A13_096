package com.example.projectakhirpam_a13.ui.view.peserta


import android.app.DatePickerDialog
import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.loket.ui.viewModel.peserta.PesertaUpdateViewModel
import com.example.loket.ui.viewModel.peserta.toPeserta
import com.example.projectakhirpam_a13.ui.custom.CostumeTopAppBar
import com.example.projectakhirpam_a13.ui.navigasi.DestinasiNavigasi
import com.example.projectakhirpam_a13.ui.viewModel.PenyediaViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale



object DestinasiUpdate : DestinasiNavigasi {
    override val route = "update peserta"
    const val idpeserta = "idpeserta"
    val routesWithArg = "$route/{$idpeserta}"
    override val titleRes = "Update Peserta"
}

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PesertaUpdateView(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: PesertaUpdateViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    val uiState = viewModel.uiState

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiUpdate.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()) // For scrolling the form
            ) {
                // Entry Pasien Form Body
                EntryBodyPeserta(
                    insertUiState = uiState.copy(),
                    onPesertaValueChange = { updatedValue ->
                        viewModel.PesertaUpdateState(updatedValue)
                    },
                    onSaveClick = {
                        uiState.insertUiEvent?.let { insertUiEvent ->
                            coroutineScope.launch {
                                viewModel.updatePeserta(
                                    idPeserta = viewModel.idPeserta,
                                    peserta = insertUiEvent.toPeserta()
                                )
                                navigateBack() // Navigate back after saving
                            }
                        }
                    }
                )
            }
        }
    }
}
