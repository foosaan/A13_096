package com.example.projectakhirpam_a13.ui.view.event

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.projectakhirpam_a13.model.Event
import com.example.projectakhirpam_a13.ui.custom.CostumeTopAppBar
import com.example.projectakhirpam_a13.ui.navigasi.DestinasiNavigasi
import com.example.projectakhirpam_a13.ui.viewModel.PenyediaViewModel
import com.example.projectakhirpam_a13.ui.viewModel.event.DetailUiState
import com.example.projectakhirpam_a13.ui.viewModel.event.EventDetailViewModel
import java.text.SimpleDateFormat
import java.util.Locale

object DestinasiDetailEvent : DestinasiNavigasi {
    override val route = "detail event"
    const val idEvent = "id_event"
    val routeWithArg = "$route/{$idEvent}"
    override val titleRes = "Detail Event"

}@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailEventView(
    idEvent: Int,
    modifier: Modifier = Modifier,
    viewModel: EventDetailViewModel = viewModel(factory = PenyediaViewModel.Factory),
    onEditClick: (Int) -> Unit = {},  // Parameter onEditClick
    navigateBack: () -> Unit


) {
    Scaffold(

        topBar = {
            CostumeTopAppBar(
                title = DestinasiDetailEvent.titleRes,
                canNavigateBack = true,
                navigateUp = navigateBack,
                onRefresh = { viewModel.getDetailEvent() }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onEditClick(idEvent) // Menggunakan id Event untuk navigasi ke update
                },
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(16.dp),
                containerColor = Color(0xFFD6ED17), // Warna FAB

            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Event"
                )
            }
        }
    ) { innerPadding ->
        val detailUiState by viewModel.detailUiState.collectAsState()

        Box(modifier = Modifier.fillMaxSize()) {
            // Background Image with Blur Effect
//            Image(
//                painter = painterResource(id = R.drawable.log), // Your background image
//                contentDescription = null,
//                modifier = Modifier
//                    .fillMaxSize()
//                    .graphicsLayer {
//                        // Apply blur effect here (you can adjust the blur radius as needed)
//                        this.shadowElevation = 8f
//                    }
//            )

            BodyDetailEvent(
                modifier = Modifier.padding(innerPadding),
                detailUiState = detailUiState,
                retryAction = { viewModel.getDetailEvent() }
            )
        }
    }
}

@Composable
fun BodyDetailEvent(
    modifier: Modifier = Modifier,
    detailUiState: DetailUiState,
    retryAction: () -> Unit = {}
) {
    when (detailUiState) {
        is DetailUiState.Loading -> {
            OnLoading(modifier = modifier.fillMaxSize())
        }

        is DetailUiState.Success -> {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                ItemDetailEvent(event  = detailUiState.event)
            }
        }

        is DetailUiState.Error -> {
            OnError(
                retryAction = retryAction,
                modifier = modifier.fillMaxSize()
            )
        }

        else -> {
            Text("Unexpected")
        }
    }
}

@Composable
fun ItemDetailEvent(event: Event) {
    val formattedTanggalLahir = remember(event.tanggal_event) {
        val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val parsedDate = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault()).parse(event.tanggal_event)
        parsedDate?.let { format.format(it) } ?: event.tanggal_event // Handle null case
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFD6ED17), //
            contentColor = Color.Black // Ganti warna teks dalam Card
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            ComponentDetailEvent(judul = "ID Event", isinya = event.id_event.toString())
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailEvent(judul = "Nama Event", isinya = event.nama_event)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailEvent(judul = "Deskripsi Event", isinya = event.deskripsi_event)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailEvent(judul = "Lokasi Event", isinya = event.lokasi_event)
        }
    }
}

@Composable
fun ComponentDetailEvent(
    judul: String,
    isinya: String
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "$judul :",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Gray
        )

        Text(
            text = isinya,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

