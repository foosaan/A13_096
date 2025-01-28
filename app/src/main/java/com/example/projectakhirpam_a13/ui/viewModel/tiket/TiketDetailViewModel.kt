package com.example.loket.ui.viewModel.tiket

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectakhirpam_a13.model.Tiket
import com.example.projectakhirpam_a13.repository.TiketRepository
import kotlinx.coroutines.launch

class DetailTiketViewModel (
    private val Tkt: TiketRepository
): ViewModel(){
    var TktUiState by mutableStateOf(DetailTiketUiState())
        private set

    fun fetchDetailTiket(id_Tiket : Int) {
        viewModelScope.launch {
            TktUiState = DetailTiketUiState(isLoading = true)
            try {
                val Tiket = Tkt.getTiketById(id_Tiket)

                TktUiState = DetailTiketUiState(detailMtgUiEvent = Tiket.toDetailTikerEvent())
            } catch (e: Exception) {
                e.printStackTrace()
                TktUiState = DetailTiketUiState(isError = true, errorMessage = "Failed to fetch details: ${e.message}")
            }
        }
    }
}

data class DetailTiketUiState(
    val detailMtgUiEvent: InsertTKTUiEvent = InsertTKTUiEvent(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = "",
){
    val isUiEventNotEmpty: Boolean
        get() = detailMtgUiEvent != InsertTKTUiEvent()
}

fun Tiket.toDetailTikerEvent(): InsertTKTUiEvent {
    return InsertTKTUiEvent(
        idTiket = id_tiket,
        id_event = id_event,
        id_peserta = id_peserta,
        Kapasitas_tiket = kapasitas_tiket,
        Harga_tiket = kapasitas_tiket,


    )
}