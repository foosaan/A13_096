//package com.example.projectakhirpam_a13.ui.view.tiket
//
//
//
//
//import android.os.Build
//import androidx.annotation.RequiresExtension
//import androidx.compose.foundation.layout.padding
//import androidx.compose.material3.ExperimentalMaterial3Api
//import androidx.compose.material3.Scaffold
//import androidx.compose.material3.TopAppBarDefaults
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.rememberCoroutineScope
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.input.nestedscroll.nestedScroll
//import androidx.lifecycle.viewmodel.compose.viewModel
//import com.example.loket.ui.viewModel.tiket.UpdateTKTViewModel
//import com.example.projectakhirpam_a13.ui.custom.CostumeTopAppBar
//import com.example.projectakhirpam_a13.ui.navigasi.DestinasiNavigasi
//import com.example.projectakhirpam_a13.ui.viewModel.PenyediaViewModel
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.delay
//import kotlinx.coroutines.launch
//import kotlinx.coroutines.withContext
//
//
//object DestinasiUpdateTiket: DestinasiNavigasi {
//    override val route = "updateTIket"
//    override val titleRes = "Update TIket"
//    const val ID_Tiket = "id TIket"
//    val routesWithArg = "$route/{$ID_Tiket}"
//}
//@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun UpdateScreenMtg(
//    onBack: () -> Unit,
//    modifier: Modifier = Modifier,
//    onNavigate:()-> Unit,
//    viewModel: UpdateTKTViewModel = viewModel(factory = PenyediaViewModel.Factory)
//){
//    val coroutineScope = rememberCoroutineScope()
//    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
//
//    Scaffold (
//        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
//        topBar = {
//            CostumeTopAppBar(
//                title = DestinasiUpdateTiket.titleRes,
//                canNavigateBack = true,
//                scrollBehavior = scrollBehavior,
//                navigateUp = onBack,
//            )
//        }
//    ){padding ->
//        EntryBodyMtg(
//            modifier = Modifier.padding(padding),
//            insertMtgUiState = viewModel.TktupdateUiState,
//            onMonitoringValueChange = viewModel::updateInsertMtgState,
//            onSaveClick = {
//                coroutineScope.launch {
//                    viewModel.updateMtg()
//                    delay(600)
//                    withContext(Dispatchers.Main){
//                        onNavigate()
//                    }
//                }
//            }
//        )
//    }
//}