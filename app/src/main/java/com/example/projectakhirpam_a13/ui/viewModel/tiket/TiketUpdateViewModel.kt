//package com.example.loket.ui.viewModel.tiket
//
//
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.setValue
//import androidx.lifecycle.SavedStateHandle
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.example.projectakhirpam_a13.repository.TiketRepository
//import kotlinx.coroutines.launch
//
//
//
//class UpdateTKTViewModel(
//    savedStateHandle: SavedStateHandle,
//    private val tkt : TiketRepository
//): ViewModel() {
//    var TktupdateUiState by mutableStateOf(InsertTKTUiState())
//        private set
//
//    private val _id_Tiket: String = checkNotNull(savedStateHandle[DetailTiketViewModel.ID_Tiket])
//
//    init {
//        viewModelScope.launch {
//            TktupdateUiState = tkt.getTiketById(_id_Tiket.toInt()).data
//                .toUiStateMtg()
//        }
//    }
//
//    fun updateInsertMtgState(insertMtgUiEvent: InsertTKTUiEvent) {
//        TktupdateUiState = InsertTKTUiState(inserTKTUiEvent = insertMtgUiEvent)
//    }
//
//    suspend fun updateMtg() {
//        viewModelScope.launch {
//            try {
//                tkt.updateTiket(_id_Tiket.toInt(), TktupdateUiState.inserTKTUiEvent.toTkt())
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }
//        }
//    }
//}