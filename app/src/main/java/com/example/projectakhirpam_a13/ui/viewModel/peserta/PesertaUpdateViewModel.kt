package com.example.loket.ui.viewModel.peserta

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectakhirpam_a13.model.Peserta
import com.example.projectakhirpam_a13.repository.PesertaRepository
import com.example.projectakhirpam_a13.ui.view.peserta.DestinasiUpdate
import kotlinx.coroutines.launch


class PesertaUpdateViewModel(
    savedStateHandle: SavedStateHandle,
    private val pesertaRepository: PesertaRepository
) : ViewModel() {
    val idPeserta: Int = checkNotNull(savedStateHandle[DestinasiUpdate.idpeserta])

    var uiState by mutableStateOf(PesertaInsertUiState())
        private set

    init {
        getPeserta()
    }

    // Fungsi untuk mengambil data pasien
    private fun getPeserta() {
        viewModelScope.launch {
            try {
                val pasien = pesertaRepository.getPesertaById(idPeserta)
                pasien?.let {
                    uiState = it.toUiStatePasien() // Update UI State dengan data pasien
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // Fungsi untuk melakukan update pasien
    fun updatePeserta(idPeserta: Int, peserta: Peserta) {
        viewModelScope.launch {
            try {
                pesertaRepository.updatePeserta(idPeserta, peserta) // Panggil repository untuk update
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // Fungsi untuk memperbarui UI State
    fun PesertaUpdateState(insertUiEvent: PesertaInsertUiEvent) {
        uiState = uiState.copy(insertUiEvent = insertUiEvent)
    }
}