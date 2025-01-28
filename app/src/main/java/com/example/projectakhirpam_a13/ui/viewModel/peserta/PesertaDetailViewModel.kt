package com.example.projectakhirpam_a13.ui.viewModel.peserta

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.loket.ui.viewModel.peserta.PesertaInsertUiEvent
import com.example.projectakhirpam_a13.model.Peserta
import com.example.projectakhirpam_a13.repository.PesertaRepository
import com.example.projectakhirpam_a13.ui.view.peserta.DestinasiDetail
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


sealed class DetailUiState {
    data class Success(val peserta: Peserta) : DetailUiState()
    object Error : DetailUiState()
    object Loading : DetailUiState()
}
class PesertaDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val pesertaRepo: PesertaRepository
) : ViewModel() {

    private val _idPeserta: Int = checkNotNull(savedStateHandle[DestinasiDetail.idpeserta])

    private val _detailUiState = MutableStateFlow<DetailUiState>(DetailUiState.Loading)
    val PesertaDetailUiState: StateFlow<DetailUiState> = _detailUiState

    init {
        getDetailPeserta()
    }

    fun getDetailPeserta() {
        viewModelScope.launch {
            try {
                _detailUiState.value = DetailUiState.Loading
                val peserta = pesertaRepo.getPesertaById(_idPeserta)
                if (peserta != null) {
                    _detailUiState.value = DetailUiState.Success(peserta)
                } else {
                    _detailUiState.value = DetailUiState.Error
                }
            } catch (e: Exception) {
                _detailUiState.value = DetailUiState.Error
            }
        }
    }
}
fun Peserta.toDetailUiEvent(): PesertaInsertUiEvent {
    return PesertaInsertUiEvent(
        idPeserta = id_peserta,
        namaPeserta = nama_peserta,
        emailPeserta = email,
        nomorTelepon = nomor_telepon
    )
}

