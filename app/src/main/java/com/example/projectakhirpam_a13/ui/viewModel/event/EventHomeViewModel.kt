package com.example.projectakhirpam_a13.ui.viewModel.event

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.projectakhirpam_a13.model.Event
import com.example.projectakhirpam_a13.repository.EventRepository
import kotlinx.coroutines.launch
import java.io.IOException

sealed class HomeUiState {


    data class Success(val Event: List<Event>) : HomeUiState()
    object Error : HomeUiState()
    object Loading : HomeUiState()
}

class EventHomeViewModel(private val EventRepository: EventRepository) : ViewModel() {
    var EventUIState: HomeUiState by mutableStateOf(HomeUiState.Loading)
        private set

    init {
        Log.d("EventHomeViewModel", "Initializing EventHomeViewModel...")
        getEvent()
    }


    fun getEvent() {
        viewModelScope.launch {
            Log.d("EventHomeViewModel", "Fetching list of events...")
            EventUIState = HomeUiState.Loading
            EventUIState = try {
                val data = EventRepository.getEvents().data
                Log.d("HomeViewModelEvent", "Successfully fetched events: ${data.size} items.")
                HomeUiState.Success(data)
            } catch (e: IOException) {
                Log.e("HomeViewModelEvent", "IO Exception while fetching events: ${e.message}")
                HomeUiState.Error
                // Optional: Show a specific message to the user
            } catch (e: HttpException) {
                Log.e("HomeViewModelEvent", "HTTP Exception while fetching events: ${e.message}")
                HomeUiState.Error
                // Optional: Show a specific message to the user
            } catch (e: Exception) {
                Log.e("HomeViewModelEvent", "Unexpected error while fetching events: ${e.message}")
                HomeUiState.Error
                // Optional: Show a specific message to the user
            }
        }
    }

    fun deleteEvent(idEvent: Int) {
        viewModelScope.launch {
            Log.d("HomeViewModelEvent", "Attempting to delete event with ID: $idEvent")
            try {
                EventRepository.deleteEvent(idEvent)
                Log.d("HomeViewModelEvent", "Successfully deleted event with ID: $idEvent")
            } catch (e: IOException) {
                Log.e("HomeViewModelEvent", "IO Exception while deleting event: ${e.message}")
                EventUIState = HomeUiState.Error
            } catch (e: HttpException) {
                Log.e("HomeViewModelEvent", "HTTP Exception while deleting event: ${e.message}")
                EventUIState = HomeUiState.Error
            } catch (e: Exception) {
                Log.e("HomeViewModelEvent", "Unexpected error while deleting event: ${e.message}")
                EventUIState = HomeUiState.Error
            }
        }
    }
}