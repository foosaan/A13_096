package com.example.projectakhirpam_a13.ui.view.event


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresExtension
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.projectakhirpam_a13.model.Event
import com.example.projectakhirpam_a13.ui.custom.CostumeTopAppBar
import com.example.projectakhirpam_a13.ui.custom.MenuButton
import com.example.projectakhirpam_a13.ui.navigasi.DestinasiNavigasi
import com.example.projectakhirpam_a13.ui.viewModel.PenyediaViewModel
import com.example.projectakhirpam_a13.ui.viewModel.event.EventHomeViewModel
import com.example.projectakhirpam_a13.ui.viewModel.event.HomeUiState
import java.time.OffsetDateTime


object DestinasiHome : DestinasiNavigasi {
    override val route = "homeEvnt"
    override val titleRes = "Home Event"
}

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenEvent(
    navigateToEventEntry: () -> Unit,
    navigateToEvent: () -> Unit,
    navigateToTransaksi: () -> Unit,
    navigateToTiket: () -> Unit,
    navigateToPengguna: () -> Unit,
    modifier: Modifier = Modifier,
    navigateToUpdateEvent: (String) -> Unit,
    onDetailClick: (Int) -> Unit = {},
    viewModel: EventHomeViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),

        topBar = {
            CostumeTopAppBar(
                title = DestinasiHome.titleRes,
                canNavigateBack = false,
                scrollBehavior = scrollBehavior,
                onRefresh = {
                    viewModel.getEvent()
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToEvent,
                shape = MaterialTheme.shapes.medium,
                containerColor = Color(0xFF2196F3),
                modifier = Modifier.padding(18.dp)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Event")
            }
            FloatingActionButton(
                onClick = navigateToEventEntry,
                shape = MaterialTheme.shapes.medium,
                containerColor = Color(0xFF2196F3),
                modifier = Modifier.padding(18.dp)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Tambah Event")
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
            EventStatus(
                homeUiState = viewModel.EventUIState,
                retryAction = { viewModel.getEvent() },
                modifier = Modifier.padding(innerPadding),
                onDetailClick = onDetailClick,
                onDeleteClick = { event ->
                    viewModel.deleteEvent(event.id_event)
                    viewModel.getEvent()
                }
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EventStatus(
    homeUiState: HomeUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDeleteClick: (Event) -> Unit = {},
    onDetailClick: (Int) -> Unit
) {
    when (homeUiState) {
        is HomeUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())
        is HomeUiState.Success ->
            if (homeUiState.Event.isEmpty()) {
                Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Tidak ada data Event")
                }
            } else {
                EventLayout(
                    event = homeUiState.Event,
                    modifier = modifier.fillMaxWidth(),
                    onDetailClick = { onDetailClick(it.id_event) },
                    onDeleteClick = { onDeleteClick(it) }
                )
            }
        is HomeUiState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
        else -> {}
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
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EventLayout(
    event: List<Event>,
    modifier: Modifier = Modifier,
    onDetailClick: (Event) -> Unit,
    onDeleteClick: (Event) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(event) { event ->
            EventCard(
                event = event,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDetailClick(event) },
                onDeleteClick = { onDeleteClick(event) }
            )
        }
    }
}
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EventCard(
    event: Event,
    modifier: Modifier = Modifier,
    onDeleteClick: (Event) -> Unit = {}
) {
    val formattedDate = try {
        val dateTime = OffsetDateTime.parse(event.tanggal_event)
        dateTime.format(DateTimeFormatter.ofPattern("dd MMMM yyyy"))
    } catch (e: Exception) {
        event.tanggal_event //
    }
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = Color(0xFF2196F3)), // Mengubah warna latar belakang card
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
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
                    text = event.nama_event,
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(Modifier.weight(1f))
                IconButton(onClick = { onDeleteClick(event) }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null
                    )
                }
            }
            Text(
                text = "Tanggal: $formattedDate",
                style = MaterialTheme.typography.titleSmall
            )
            Text(
                text = "Lokasi Event: ${event.lokasi_event}",
                style = MaterialTheme.typography.titleSmall
            )
            Text(
                text = "Id Event: ${event.id_event}",
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}