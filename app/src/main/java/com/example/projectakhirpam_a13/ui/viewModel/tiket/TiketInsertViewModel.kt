package com.example.loket.ui.viewModel.tiket


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectakhirpam_a13.model.AllEventResponse
import com.example.projectakhirpam_a13.model.AllPesertaResponse
import com.example.projectakhirpam_a13.model.Event
import com.example.projectakhirpam_a13.model.Peserta
import com.example.projectakhirpam_a13.model.Tiket
import com.example.projectakhirpam_a13.repository.EventRepository
import com.example.projectakhirpam_a13.repository.PesertaRepository
import com.example.projectakhirpam_a13.repository.TiketRepository
import kotlinx.coroutines.launch

class InsertTktViewModel(
    private val tkt: TiketRepository,
    private val pstr: PesertaRepository,
    private val evn : EventRepository
): ViewModel(){

    var TKTUiState by mutableStateOf(InsertTKTUiState())
        private set

    init {
        loadHwnNKdgNPtgs()
    }

    private fun loadHwnNKdgNPtgs(){
        viewModelScope.launch {
            try {
                val EventResponse: AllEventResponse = evn.getEvents()
                val EventList: List<Event> = EventResponse.data

                val PesertaResponse: AllPesertaResponse = pstr.getPeserta()
                val PesertaList: List<Peserta> = PesertaResponse.data


                TKTUiState = TKTUiState.copy(
                    EventOption = EventList.map {
                        it.toDropdownOptionEvent()
                    },
                    PesertaOption = PesertaList.map {
                        it.toDropdownOptionPeserta()
                    },
                )

            } catch (e: Exception){
                e.printStackTrace()
            }
        }
    }

    fun updateInsertMtgState(insertMtgUiEvent: InsertTKTUiEvent){
        TKTUiState = InsertTKTUiState(inserTKTUiEvent = insertMtgUiEvent)
    }

    suspend fun insertMtg(){
        viewModelScope.launch {
            try {
                tkt.insertTiket(TKTUiState.inserTKTUiEvent.toTkt())
            } catch (e: Exception){
                e.printStackTrace()
            }
        }
    }


}

data class InsertTKTUiState(
    val inserTKTUiEvent: InsertTKTUiEvent = InsertTKTUiEvent(),
    val EventOption: List<DropEvent> = emptyList(),
    val PesertaOption: List<DropPeserta> = emptyList(),
)

data class InsertTKTUiEvent(
    val idTiket: Int = 0,
    val id_event: Int = 0,
    val id_peserta: Int = 0,
    val Kapasitas_tiket: String = "",
    val Harga_tiket: String = "",
)

fun InsertTKTUiEvent.toTkt(): Tiket = Tiket(
    id_tiket = idTiket ,
    id_event = id_event,
    id_peserta = id_peserta,
    kapasitas_tiket = Kapasitas_tiket,
    harga_tiket = Harga_tiket

)

fun Tiket.toUiStateTKT(): InsertTKTUiState = InsertTKTUiState(
    inserTKTUiEvent = toInsertTKRUiEvent()
)

fun Tiket.toInsertTKRUiEvent(): InsertTKTUiEvent = InsertTKTUiEvent(
    idTiket = id_tiket,
    id_peserta = id_peserta,
    id_event = id_event,
    Kapasitas_tiket = kapasitas_tiket,
    Harga_tiket = harga_tiket
)
data class DropEvent(
    val id_event: Int,
    val label: String
)

data class DropPeserta(
    val id_peserta: Int,
    val label: String
)


fun Event.toDropdownOptionEvent() = DropEvent(
    id_event = id_event,
    label = id_event.toString()
)

fun Peserta.toDropdownOptionPeserta() = DropPeserta(
    id_peserta = id_peserta,
    label = id_peserta.toString()
)


