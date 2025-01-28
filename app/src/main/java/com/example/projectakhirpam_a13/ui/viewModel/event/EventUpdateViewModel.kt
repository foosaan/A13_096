package com.example.projectakhirpam_a13.ui.viewModel.event

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectakhirpam_a13.model.Event
import com.example.projectakhirpam_a13.repository.EventRepository
import com.example.projectakhirpam_a13.ui.view.event.DestinasiUpdateEvent
import kotlinx.coroutines.launch

class EventUpdateViewModel(
    savedStateHandle: SavedStateHandle,
    private val EventRepository: EventRepository
) : ViewModel() {
    val idEvent: Int = checkNotNull(savedStateHandle[DestinasiUpdateEvent.idEvent])

    var uiState by mutableStateOf(EventInsertUiState())
        private set

    init {
        idEvent
    }

    // Fungsi untuk mengambil data event
    private fun getEvent() {
        viewModelScope.launch {
            try {
                val Event = EventRepository.getEventById(idEvent)
                Event?.let {
                    uiState = it.toEventUiState() // Update UI State dengan data Event
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // Fungsi untuk melakukan update event
    fun updateEvent(idEvent: Int, Event: Event) {
        viewModelScope.launch {
            try {
                EventRepository.updateEvent(idEvent, Event) // Panggil repository untuk update
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // Fungsi untuk memperbarui UI State
    fun EventupdateState(insertUiEvent: EventInsertUiEvent) {
        uiState = uiState.copy(insertUiEvent = insertUiEvent)
    }
}

fun Event.toInsertUIEvent(): EventInsertUiState = EventInsertUiState(
    insertUiEvent = this.toInsertUiEvent()
)

fun Event.toInsertUiEvent(): EventInsertUiEvent = EventInsertUiEvent(
    idEvent = id_event,
    namaEvent = nama_event,
    tanggalEvent = tanggal_event,
    deskripsiEvent = deskripsi_event,
    lokasiEvent = lokasi_event
)