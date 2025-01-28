//package com.example.projectakhirpam_a13.ui.view.transaksi
//
//
//import androidx.compose.material.icons.Icons
//import androidx.compose.runtime.remember
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.unit.sp
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.material.icons.filled.Edit
//import androidx.compose.material3.*
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.collectAsState
//import androidx.compose.runtime.getValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.graphics.graphicsLayer
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.unit.dp
//import androidx.lifecycle.viewmodel.compose.viewModel
//import com.example.projectakhirpam_a13.model.Transaksi
//import com.example.projectakhirpam_a13.ui.custom.CostumeTopAppBar
//import com.example.projectakhirpam_a13.ui.navigasi.DestinasiNavigasi
//import com.example.projectakhirpam_a13.ui.view.event.OnError
//import com.example.projectakhirpam_a13.ui.view.event.OnLoading
//import com.example.projectakhirpam_a13.ui.viewModel.PenyediaViewModel
//import com.example.projectakhirpam_a13.ui.viewModel.peserta.DetailUiState
//import java.text.SimpleDateFormat
//import java.util.Locale
//
//object DestinasiDetail : DestinasiNavigasi {
//    override val route = "detail transaksi"
//    const val idtransaksi = "idtransaksi"
//    val routeWithArg = "$route/{$idtransaksi}"
//    override val titleRes = "Detail Transaksi"
//}
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun DetailTransaksiView(
//    idTransaksi: Int,
//    modifier: Modifier = Modifier,
//    viewModel: DetailTransaksiViewModel = viewModel(factory = PenyediaViewModel.Factory),
//    onEditClick: (Int) -> Unit = {}, // Callback untuk tombol edit
//    navigateBack: () -> Unit
//) {
//    Scaffold(
//        topBar = {
//            CostumeTopAppBar(
//                title = DestinasiDetail.titleRes,
//                canNavigateBack = true,
//                navigateUp = navigateBack,
//                onRefresh = { viewModel.getDetailTransaksi() }
//            )
//        },
//        floatingActionButton = {
//            FloatingActionButton(
//                onClick = { onEditClick(idTransaksi) }, // Navigasi ke halaman edit
//                shape = MaterialTheme.shapes.medium,
//                containerColor = Color(0xFFD6ED17), // Warna FAB
//                modifier = Modifier.padding(16.dp)
//            ) {
//                Icon(
//                    imageVector = Icons.Default.Edit,
//                    contentDescription = "Edit Pasien"
//                )
//            }
//        }
//    ) { innerPadding ->
//        val detailUiState by viewModel.detailUiState.collectAsState()
//
//        Box(modifier = Modifier.fillMaxSize()) {
//
//            // Content
//            when (val state = detailUiState) {
//                is DetailUiState.Loading -> {
//                    OnLoading(modifier = Modifier.fillMaxSize())
//                }
//                is DetailUiState.Success -> {
//                    Column(
//                        modifier = modifier
//                            .fillMaxSize()
//                            .padding(innerPadding)
//                    ) {
//                        ItemDetailTransaksi(transaksi = = state.transaksi)
//                    }
//                }
//                is DetailUiState.Error -> {
//                    OnError(
//                        retryAction = { viewModel.getDetailPasien() },
//                        modifier = modifier.fillMaxSize()
//                    )
//                }
//                else -> {
//                    Text("Unexpected state")
//                }
//            }
//        }
//    }
//}
//
//@Composable
//fun ItemDetailTransaksi(transaksi: Transaksi) {
//
//    Card(
//        modifier = Modifier.fillMaxWidth(),
//        colors = CardDefaults.cardColors(
//            containerColor = Color(0xFFD6ED17), // Ganti warna Card menjadi biru muda
//            contentColor = Color.Black // Ganti warna teks dalam Card
//        )
//    ) {
//        Column(modifier = Modifier.padding(16.dp)) {
//            ComponentDetailTransaksi(judul = "ID Transaksi", isinya = transaksi.id_transaksi.toString())
//            Spacer(modifier = Modifier.padding(4.dp))
//            ComponentDetailTransaksi(judul = "ID Tiket", isinya = transaksi.id_tiket.toString())
//            Spacer(modifier = Modifier.padding(4.dp))
//            ComponentDetailTransaksi(judul = "Tanggal Transaksi", isinya = transaksi.tanggal_transaksi)
//            Spacer(modifier = Modifier.padding(4.dp))
//            ComponentDetailTransaksi(judul = "Nama Event", isinya = transaksi.nama_event)
//            Spacer(modifier = Modifier.padding(4.dp))
//            ComponentDetailTransaksi(judul = "Nama Peserta", isinya = transaksi.nama_peserta)
//            Spacer(modifier = Modifier.padding(4.dp))
//            ComponentDetailTransaksi(judul = "Jumalah Pembayaran", isinya = transaksi.jumlah_pembayaran.toString())
//        }
//    }
//}
//
//@Composable
//fun ComponentDetailTransaksi(judul: String, isinya: String) {
//    Column(
//        modifier = Modifier.fillMaxWidth(),
//        horizontalAlignment = Alignment.Start
//    ) {
//        Text(
//            text = "$judul :",
//            fontSize = 20.sp,
//            fontWeight = FontWeight.Bold,
//            color = Color.White
//        )
//
//        Text(
//            text = isinya,
//            fontSize = 20.sp,
//            fontWeight = FontWeight.Bold
//        )
//    }
//}
