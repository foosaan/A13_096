package com.example.projectakhirpam_a13.ui.viewModel.event

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectakhirpam_a13.model.Event
import com.example.projectakhirpam_a13.repository.EventRepository
import com.example.projectakhirpam_a13.ui.view.event.DestinasiDetailEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


sealed class DetailUiState {
    data class Success(val event: Event) : DetailUiState()
    object Error : DetailUiState()
    object Loading : DetailUiState()
}
class EventDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val EventRepo: EventRepository
) : ViewModel() {

    private val _idEvent: Int = checkNotNull(savedStateHandle[DestinasiDetailEvent.idEvent])

    private val _detailUiState = MutableStateFlow<DetailUiState>(DetailUiState.Loading)
    val detailUiState: StateFlow<DetailUiState> = _detailUiState

    init {
        getDetailEvent()
    }

    fun getDetailEvent() {
        viewModelScope.launch {
            try {
                _detailUiState.value = DetailUiState.Loading
                val event = EventRepo.getEventById(_idEvent)
                if (Event != null) {
                    _detailUiState.value = DetailUiState.Success(event)
                } else {
                    _detailUiState.value = DetailUiState.Error
                }
            } catch (e: Exception) {
                _detailUiState.value = DetailUiState.Error
            }
        }
    }
}
fun Event.toDetailUiEvent(): EventInsertUiEvent {
    return EventInsertUiEvent(
        idEvent = id_event,
        namaEvent = nama_event,
        deskripsiEvent = deskripsi_event,
        tanggalEvent = tanggal_event,
        lokasiEvent = lokasi_event
    )

}
