package com.example.projectakhirpam_a13.ui.view.peserta


import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.remember
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.projectakhirpam_a13.model.Peserta
import com.example.projectakhirpam_a13.ui.custom.CostumeTopAppBar
import com.example.projectakhirpam_a13.ui.navigasi.DestinasiNavigasi
import com.example.projectakhirpam_a13.ui.view.event.OnError
import com.example.projectakhirpam_a13.ui.view.event.OnLoading
import com.example.projectakhirpam_a13.ui.viewModel.PenyediaViewModel
import com.example.projectakhirpam_a13.ui.viewModel.peserta.DetailUiState
import com.example.projectakhirpam_a13.ui.viewModel.peserta.PesertaDetailViewModel
import java.text.SimpleDateFormat
import java.util.Locale

object DestinasiDetail : DestinasiNavigasi {
    override val route = "detail peserta"
    const val idpeserta = "idpeserta"
    val routeWithArg = "$route/{$idpeserta}"
    override val titleRes = "Detail Peserta"
}

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PesertaDetailView(
    idPeserta: Int,
    modifier: Modifier = Modifier,
    viewModel: PesertaDetailViewModel = viewModel(factory = PenyediaViewModel.Factory),
    onEditClick: (Int) -> Unit = {}, // Callback untuk tombol edit
    navigateBack: () -> Unit
) {
    Scaffold(
        topBar = {
            CostumeTopAppBar(
                title = DestinasiDetail.titleRes,
                canNavigateBack = true,
                navigateUp = navigateBack,
                onRefresh = { viewModel.getDetailPeserta() }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onEditClick(idPeserta) }, // Navigasi ke halaman edit
                shape = MaterialTheme.shapes.medium,
                containerColor = Color(0xFF2196F3),
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Peserta"
                )
            }
        }
    ) { innerPadding ->
        val detailUiState by viewModel.PesertaDetailUiState.collectAsState()

        Box(modifier = Modifier.fillMaxSize()) {
            // Background Image with Blur Effect

            // Content
            when (val state = detailUiState) {
                is DetailUiState.Loading -> {
                    OnLoading(modifier = Modifier.fillMaxSize())
                }
                is DetailUiState.Success -> {
                    Column(
                        modifier = modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        ItemDetailPeserta(peserta = state.peserta)
                    }
                }
                is DetailUiState.Error -> {
                    OnError(
                        retryAction = { viewModel.getDetailPeserta() },
                        modifier = modifier.fillMaxSize()
                    )
                }
                else -> {
                    Text("Unexpected state")
                }
            }
        }
    }
}

@Composable
fun ItemDetailPeserta(peserta: Peserta) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFD6ED17), // Ganti warna Card menjadi biru muda
            contentColor = Color.Black // Ganti warna teks dalam Card
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            ComponentDetailPeserta(judul = "ID Peserta", isinya = peserta.id_peserta.toString())
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailPeserta(judul = "Nama", isinya = peserta.nama_peserta)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailPeserta(judul = "Alamat", isinya = peserta.email)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailPeserta(judul = "Nomor Telepon", isinya = peserta.nomor_telepon)
        }
    }
}

@Composable
fun ComponentDetailPeserta(judul: String, isinya: String) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "$judul :",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )

        Text(
            text = isinya,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
