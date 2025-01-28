package com.example.loket.ui.view.peserta

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.projectakhirpam_a13.model.Peserta
import com.example.projectakhirpam_a13.ui.custom.CostumeTopAppBar
import com.example.projectakhirpam_a13.ui.custom.MenuButton
import com.example.projectakhirpam_a13.ui.navigasi.DestinasiNavigasi
import com.example.projectakhirpam_a13.ui.viewModel.PenyediaViewModel
import com.example.projectakhirpam_a13.ui.viewModel.peserta.HomeUiStatePsrt
import com.example.projectakhirpam_a13.ui.viewModel.peserta.PesertaHomeViewModel
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

object DestinasiHomePeserta : DestinasiNavigasi {
    override val route = "home Peserta"
    override val titleRes = "Home Peserta"
}

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenPeserta(
    navigateToPesertaEntry: () -> Unit,
    navigateToEvent: () -> Unit,
    navigateToTransaksi: () -> Unit,
    navigateToTiket: () -> Unit,
    navigateToPengguna: () -> Unit,
    modifier: Modifier = Modifier,
    navigateToUpdateEvent: (String) -> Unit,
    onDetailClick: (Int) -> Unit = {},
    viewModel: PesertaHomeViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiHomePeserta.titleRes,
                canNavigateBack = false,
                scrollBehavior = scrollBehavior,
                onRefresh = {
                    viewModel.getPeserta()
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToTiket,
                shape = MaterialTheme.shapes.medium,
                containerColor = Color(0xFF2196F3),
                modifier = Modifier.padding(18.dp)
            ){
                Icon(imageVector = Icons.Default.Add, contentDescription = "pengguna")
            }
            FloatingActionButton(
                onClick = navigateToPesertaEntry,
                shape = MaterialTheme.shapes.medium,
                containerColor = Color(0xFFD6ED17), // Warna FAB
                modifier = Modifier.padding(18.dp)
            ) {

                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Peserta")
            }
        },
        bottomBar = {
            MenuButton(
                onEventClick = navigateToEvent,
                onTiketClick = navigateToTiket,
                onPenggunaClick = navigateToPengguna,
                onTransaksiClick = navigateToTransaksi,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )
        }
    ) { innerPadding ->

        Box(modifier = Modifier.fillMaxSize()) {
            // Gambar background blur
//            Image(
//                painter = painterResource(id = R.drawable.log), // Ganti dengan gambar yang sesuai
//                contentDescription = "Background Image",
//                modifier = Modifier
//                    .fillMaxSize()
//                    .graphicsLayer {
//                        // Blur Effect
//                        shadowElevation = 10f
//                    }
//            )
            // Konten utama
            HomeStatus(
                UiState = viewModel.pesertaUIState,
                retryAction = { viewModel.getPeserta() },
                modifier = Modifier.padding(innerPadding),
                onDetailClick = onDetailClick,
                onDeleteClick = { peserta ->
                    viewModel.deletePeserta(peserta.id_peserta)
                    viewModel.getPeserta()
                }
            )
        }
    }
}

@Composable
fun HomeStatus(
    UiState: HomeUiStatePsrt,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDeleteClick: (Peserta) -> Unit = {},
    onDetailClick: (Int) -> Unit
) {
    when (UiState) {
        is HomeUiStatePsrt.Loading -> OnLoading(modifier = modifier.fillMaxSize())
        is HomeUiStatePsrt.Success -> {
            if (UiState.peserta.isEmpty()) {
                Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Tidak ada data peserta")
                }
            } else {
                PesertaLayout(
                    peserta = UiState.peserta,
                    modifier = modifier.fillMaxWidth(),
                    onDetailClick = { onDetailClick(it.id_peserta) },
                    onDeleteClick = { onDeleteClick(it) }
                )
            }
        }
        is HomeUiStatePsrt.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
    }
}

@Composable
fun OnLoading(modifier: Modifier = Modifier) {
}

@Composable
fun OnError(retryAction: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
    }
}

@Composable
fun PesertaLayout(
    peserta: List<Peserta>,
    modifier: Modifier = Modifier,
    onDetailClick: (Peserta) -> Unit,
    onDeleteClick: (Peserta) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(peserta) { peserta ->
            PesertaCard(
                peserta = peserta,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDetailClick(peserta) },
                onDeleteClick = { onDeleteClick(peserta) }
            )
        }
    }
}

@Composable
fun PesertaCard(
    peserta: Peserta,
    modifier: Modifier = Modifier,
    onDeleteClick: (Peserta) -> Unit = {}
) {
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF2196F3)) // Mengubah warna latar belakang card
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = peserta.nama_peserta,
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(Modifier.weight(1f))
                IconButton(onClick = { onDeleteClick(peserta) }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null,
                    )
                }
            }

            Text(
                text = "Alamat: ${peserta.email}",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "Nomor Telepon: ${peserta.nomor_telepon}",
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}
